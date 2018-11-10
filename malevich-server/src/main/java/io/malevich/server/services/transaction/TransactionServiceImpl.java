package io.malevich.server.services.transaction;

import io.malevich.server.exceptions.AccountStateException;
import io.malevich.server.repositories.accountstate.AccountStateDao;
import io.malevich.server.repositories.transaction.TransactionDao;
import io.malevich.server.domain.*;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountStateDao accountStateDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionEntity> findAll() {
        return this.transactionDao.findAll();
    }

    private TransactionEntity insertTransaction(TransactionTypeEntity transactionType,
                                                CounterpartyEntity party,
                                                CounterpartyEntity counterparty,
                                                ArtworkStockEntity artworkStock,
                                                Double amount,
                                                Long quantity) {

        TransactionEntity transaction = new TransactionEntity();
        transaction.setParty(party);
        transaction.setCounterparty(counterparty);
        transaction.setArtworkStock(artworkStock);
        transaction.setAmount(amount);
        transaction.setQuantity(quantity);
        transaction.setType(transactionType);
        return transactionDao.save(transaction);
    }

    private void insertAccountState(CounterpartyEntity party, ArtworkStockEntity artworkStock, Double amount, Long quantity) {
        Long artworkStockId = null;
        if (artworkStock != null)
            artworkStockId = artworkStock.getId();
        AccountStateEntity accountState = accountStateDao.findByArtworkStock_IdAndParty_Id(artworkStockId, party.getId());

        if (accountState == null) {
            accountState = new AccountStateEntity();
            accountState.setAmount(0D);
            accountState.setQuantity(0L);
            accountState.setParty(party);
            accountState.setArtworkStock(artworkStock);
        }

        CounterpartyEntity malevich = counterpartyService.getMalevich();

        if (accountState.getArtworkStock() == null && quantity != null && quantity != 0)
            throw new AccountStateException();

        Double newAmount = accountState.getAmount() + amount;
        if (accountState.getParty().getId() != malevich.getId() && newAmount < 0)
            throw new AccountStateException();
        accountState.setAmount(newAmount);

        Long newQuantity = accountState.getQuantity() + quantity;
        if (accountState.getParty().getId() != malevich.getId() && newQuantity < 0)
            throw new AccountStateException();
        accountState.setQuantity(newQuantity);

        accountStateDao.save(accountState);
    }

    @Override
    @Transactional
    public void createTransaction(TransactionTypeEntity transactionType,
                                  CounterpartyEntity party,
                                  CounterpartyEntity counterparty,
                                  ArtworkStockEntity artworkStock,
                                  Double amount,
                                  Long quantity) {
        insertTransaction(transactionType, party, counterparty, artworkStock, amount, quantity);

        if (amount != null && amount != 0)
            insertAccountState(party, null, amount, 0L);

        if (quantity != null && quantity != 0)
            insertAccountState(party, artworkStock, 0D, quantity);
    }

    @Override
    @Transactional
    public void createTransactionAndReverse(TransactionTypeEntity transactionType,
                                            CounterpartyEntity party,
                                            CounterpartyEntity counterparty,
                                            ArtworkStockEntity artworkStock,
                                            Double amount,
                                            Long quantity) {
        createTransaction(transactionType, party, counterparty, artworkStock, amount, quantity);
        createTransaction(transactionType, counterparty, party, artworkStock, -amount, -quantity);
    }

    @Override
    @Transactional
    public void createArtworkStock(ArtworkStockEntity artworkStockEntity) {
        CounterpartyEntity counterpartyEntity = counterpartyService.getCurrent();
        CounterpartyEntity malevichEntity = counterpartyService.getMalevich();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0002").get();

        try {
            createTransactionAndReverse(transactionTypeEntity, counterpartyEntity, malevichEntity, artworkStockEntity, 0D, 1L);
        } catch (AccountStateException e) {
            // this method only increases balance
        }
    }


    @Override
    @Transactional
    public void applyPayment(PaymentsEntity paymentsEntity) {
        CounterpartyEntity malevichEntity = counterpartyService.getMalevich();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0001").get();

        createTransactionAndReverse(transactionTypeEntity, paymentsEntity.getParty(), malevichEntity, null, paymentsEntity.getAmount(), 0L);
    }



}
