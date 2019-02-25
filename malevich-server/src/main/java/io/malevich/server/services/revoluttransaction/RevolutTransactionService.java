package io.malevich.server.services.revoluttransaction;

import io.malevich.server.domain.RevolutTransactionEntity;
import org.springframework.stereotype.Service;

@Service
public interface RevolutTransactionService {

    RevolutTransactionEntity save(RevolutTransactionEntity entity);

    RevolutTransactionEntity findById(String id);

    void processDeposits();
}
