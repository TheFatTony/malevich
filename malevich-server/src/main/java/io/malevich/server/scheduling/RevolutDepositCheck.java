package io.malevich.server.scheduling;

import io.malevich.server.services.revoluttransaction.RevolutTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile({"test", "prod"})
@Component
public class RevolutDepositCheck {

    @Autowired
    private RevolutTransactionService revolutTransactionService;

//    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkTransactions() {
        revolutTransactionService.processDeposits();
    }

}
