package io.malevich.server.services.transaction;

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
    private GalleryService galleryService;

    @Autowired
    private TraderService traderService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionEntity> findAll() {
        return this.transactionDao.findAll();
    }

    private void createTransaction(TransactionTypeEntity transactionType,
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
        transactionDao.save(transaction);
    }

    private void createAccountState(CounterpartyEntity party, ArtworkStockEntity artworkStock, Double amount, Long quantity) {
        Long artworkStockId = null;
        if (artworkStock != null)
            artworkStockId = artworkStock.getId();
        AccountStateEntity accountState = accountStateDao.findByArtworkStock_IdAndParty_Id(artworkStockId, party.getId());
        if (accountState == null) {
            accountState = new AccountStateEntity();
            accountState.setAmount(amount);
            accountState.setQuantity(quantity);
            accountState.setParty(party);
            accountState.setArtworkStock(artworkStock);
        } else {
            accountState.setAmount(accountState.getAmount() + amount);
            accountState.setQuantity(quantity);
            accountState.setArtworkStock(artworkStock);
        }
        accountStateDao.save(accountState);
    }

    private void checkAccountState(CounterpartyEntity party, ArtworkStockEntity artworkStock, Double amount, Long quantity) {
        Long artworkStockId = null;
        if (artworkStock != null)
            artworkStockId = artworkStock.getId();
        AccountStateEntity accountState = accountStateDao.findByArtworkStock_IdAndParty_Id(artworkStockId, party.getId());
        if (accountState == null) {

        } else {

        }
    }


    @Override
    @Transactional
    public void createArtworkStock(ArtworkStockEntity artworkStockEntity) {
        GalleryEntity galleryEntity = galleryService.getCurrent();
        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId());
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0002").get();


        createAccountState(malevichEntity, artworkStockEntity, 0D, -1L);
        createAccountState(counterpartyEntity, artworkStockEntity, 0D, 1L);
        createTransaction(transactionTypeEntity, counterpartyEntity, malevichEntity, artworkStockEntity, 0D, 1L);
        createTransaction(transactionTypeEntity, malevichEntity, counterpartyEntity, artworkStockEntity, 0D, -1L);
    }

    @Override
    @Transactional
    public void placeAsk(OrderEntity orderEntity) {
//        GalleryEntity galleryEntity = galleryService.getCurrent();
//        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId());
//        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
//        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0004").get();
//
//
//        createAccountState(malevichEntity, orderEntity.getArtworkStock(), 0D, 1L);
//        createAccountState(counterpartyEntity, orderEntity.getArtworkStock(), 0D, 0L);
//        createTransaction(transactionTypeEntity, counterpartyEntity, malevichEntity, orderEntity.getArtworkStock(), 0D, -1L);
//        createTransaction(transactionTypeEntity, malevichEntity, counterpartyEntity, orderEntity.getArtworkStock(), 0D, 1L);
    }


    @Override
    @Transactional
    public void addAccountStates(PaymentsEntity paymentsEntity) {
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0001").get();

        createAccountState(malevichEntity, null, -paymentsEntity.getAmount(), 0L);
        createAccountState(paymentsEntity.getParty(), null, paymentsEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, paymentsEntity.getParty(), malevichEntity, null, paymentsEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, malevichEntity, paymentsEntity.getParty(), null, -paymentsEntity.getAmount(), 0L);
    }

    @Override
    @Transactional
    public void placeBid(OrderEntity orderEntity) {
        TraderEntity traderEntity = traderService.getCurrentTrader();
        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByTraderId(traderEntity.getId());
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0003").get();

        createAccountState(malevichEntity, null, orderEntity.getAmount(), 0L);
        createAccountState(counterpartyEntity, null, -orderEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, counterpartyEntity, malevichEntity, null, -orderEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, malevichEntity, counterpartyEntity, null, orderEntity.getAmount(), 0L);
    }

    @Override
    @Transactional
    public void cancelBid(OrderEntity orderEntity) {
        CounterpartyEntity counterpartyEntity = orderEntity.getParty();
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0006").get();

        createAccountState(malevichEntity, null, -orderEntity.getAmount(), 0L);
        createAccountState(counterpartyEntity, null, orderEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, counterpartyEntity, malevichEntity, null, orderEntity.getAmount(), 0L);
        createTransaction(transactionTypeEntity, malevichEntity, counterpartyEntity, null, -orderEntity.getAmount(), 0L);
    }

    @Override
    @Transactional
    public void cancelAsk(OrderEntity orderEntity) {
        CounterpartyEntity counterpartyEntity = orderEntity.getParty();
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();

        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0007").get();

        ArtworkStockEntity artworkStock = orderEntity.getArtworkStock();

        createAccountState(malevichEntity, artworkStock, 0D, -1L);
        createAccountState(counterpartyEntity, artworkStock, 0D, 1L);
        createTransaction(transactionTypeEntity, counterpartyEntity, malevichEntity, artworkStock, 0D, 1L);
        createTransaction(transactionTypeEntity, malevichEntity, counterpartyEntity, artworkStock, 0D, -1L);
    }

    @Override
    @Transactional
    public void buySell(TradeHistoryEntity tradeHistoryEntity) {
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0005").get();
        CounterpartyEntity sellerEntity = counterpartyService.findById(tradeHistoryEntity.getAskOrder().getParty().getId()).get();
        CounterpartyEntity buyerEntity = counterpartyService.findById(tradeHistoryEntity.getBidOrder().getParty().getId()).get();

        createAccountState(sellerEntity, tradeHistoryEntity.getArtworkStock(), tradeHistoryEntity.getAmount(), -1L);
        createAccountState(buyerEntity, tradeHistoryEntity.getArtworkStock(), -tradeHistoryEntity.getAmount(), 1L);
        createTransaction(transactionTypeEntity, sellerEntity, buyerEntity, tradeHistoryEntity.getArtworkStock(), tradeHistoryEntity.getAmount(), -1L);
        createTransaction(transactionTypeEntity, buyerEntity, sellerEntity, tradeHistoryEntity.getArtworkStock(), -tradeHistoryEntity.getAmount(), 1L);
    }


}
