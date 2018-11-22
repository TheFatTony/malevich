package io.malevich.server.services.transaction;

import io.malevich.server.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TransactionService {

    List<TransactionEntity> findAll();

    void createTransaction(TransactionTypeEntity transactionType,
                           TransactionGroupEntity group,
                           CounterpartyEntity party,
                           CounterpartyEntity counterparty,
                           ArtworkStockEntity artworkStock,
                           Double amount,
                           Long quantity);

    void createTransactionAndReverse(TransactionTypeEntity transactionType,
                                     TransactionGroupEntity group,
                                     CounterpartyEntity party,
                                     CounterpartyEntity counterparty,
                                     ArtworkStockEntity artworkStock,
                                     Double amount,
                                     Long quantity);

    void createArtworkStock(ArtworkStockEntity artworkStockEntity, TransactionGroupEntity transactionGroup);

}
