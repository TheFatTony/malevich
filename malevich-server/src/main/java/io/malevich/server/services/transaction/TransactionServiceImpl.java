package io.malevich.server.services.transaction;

import io.malevich.server.dao.accountstate.AccountStateDao;
import io.malevich.server.dao.transaction.TransactionDao;
import io.malevich.server.entity.*;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountStateDao accountStateDao;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionEntity> findAll() {
        return this.transactionDao.findAll();
    }

    @Override
    @Transactional
    public void createArtworkStock(ArtworkStockEntity artworkStockEntity) {
        GalleryEntity galleryEntity = galleryService.getCurrent();
        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId());
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0002").get();

        TransactionEntity transaction = new TransactionEntity();
        transaction.setParty(malevichEntity);
        transaction.setCounterparty(counterpartyEntity);
        transaction.setArtworkStock(artworkStockEntity);
        transaction.setAmount((double) 0);
        transaction.setQuantity((long) -1);
        transaction.setType(transactionTypeEntity);

        transactionDao.save(transaction);

        AccountStateEntity accountState = new AccountStateEntity();
        accountState.setAmount((double) 0);
        accountState.setQuantity((long) 0);
        accountState.setParty(malevichEntity);
        accountState.setArtworkStock(artworkStockEntity);

        accountStateDao.save(accountState);


        TransactionEntity transactionReverse = new TransactionEntity();
        transactionReverse.setParty(counterpartyEntity);
        transactionReverse.setCounterparty(malevichEntity);
        transactionReverse.setArtworkStock(artworkStockEntity);
        transactionReverse.setAmount((double) 0);
        transactionReverse.setQuantity((long) 1);
        transactionReverse.setType(transactionTypeEntity);

        transactionDao.save(transactionReverse);

        AccountStateEntity accountStateReverse = new AccountStateEntity();
        accountStateReverse.setAmount((double) 0);
        accountStateReverse.setQuantity((long) 1);
        accountStateReverse.setParty(counterpartyEntity);
        accountStateReverse.setArtworkStock(artworkStockEntity);

        accountStateDao.save(accountStateReverse);

    }

    @Override
    @Transactional
    public void placeBid(OrderEntity orderEntity) {
        GalleryEntity galleryEntity = galleryService.getCurrent();
        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId());
        CounterpartyEntity malevichEntity = counterpartyService.findById(1L).get();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.findById("0004").get();

        TransactionEntity transaction = new TransactionEntity();
        transaction.setParty(malevichEntity);
        transaction.setCounterparty(counterpartyEntity);
        transaction.setArtworkStock(orderEntity.getArtworkStock());
        transaction.setAmount(-orderEntity.getAmount());
        transaction.setQuantity((long) 1);
        transaction.setType(transactionTypeEntity);

        transactionDao.save(transaction);

        AccountStateEntity accountState = accountStateDao.findByArtworkStock_IdAndParty_Id(orderEntity.getArtworkStock().getId(), malevichEntity.getId());
        accountState.setAmount((double) 0);
        accountState.setQuantity((long) 1);

        accountStateDao.save(accountState);


        TransactionEntity transactionReverse = new TransactionEntity();
        transactionReverse.setParty(counterpartyEntity);
        transactionReverse.setCounterparty(malevichEntity);
        transactionReverse.setArtworkStock(orderEntity.getArtworkStock());
        transactionReverse.setAmount(orderEntity.getAmount());
        transactionReverse.setQuantity((long) -1);
        transactionReverse.setType(transactionTypeEntity);

        transactionDao.save(transactionReverse);

        AccountStateEntity accountStateReverse = accountStateDao.findByArtworkStock_IdAndParty_Id(orderEntity.getArtworkStock().getId(), counterpartyEntity.getId());
        accountStateReverse.setAmount((double) 0);
        accountStateReverse.setQuantity((long) 0);

        accountStateDao.save(accountStateReverse);
    }


}
