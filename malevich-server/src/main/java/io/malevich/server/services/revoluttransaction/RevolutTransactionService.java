package io.malevich.server.services.revoluttransaction;

import io.malevich.server.domain.RevolutTransactionEntity;
import io.malevich.server.revolut.model.TransactionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface RevolutTransactionService {

    RevolutTransactionEntity save(RevolutTransactionEntity entity);

    RevolutTransactionEntity findById(String id);

    void processRevolutTopUpTransaction(TransactionModel transaction, boolean force);

    void processDeposits();
}
