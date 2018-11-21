package io.malevich.server.services.payments;

import io.malevich.server.domain.TransactionGroupEntity;
import io.malevich.server.exceptions.AccountStateException;
import io.malevich.server.repositories.payments.PaymentsDao;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.TraderEntity;
import io.malevich.server.repositories.transactiongroup.TransactionGroupDao;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsDao paymentsDao;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TraderService traderService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TransactionGroupDao transactionGroupDao;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentsEntity> findAll() {
        return this.paymentsDao.findAll();
    }

    @Override
    @Transactional
    public void insert(PaymentsEntity paymentsEntity) {

        TraderEntity traderEntity = traderService.getCurrentTrader();
        CounterpartyEntity trader = counterpartyService.findCounterpartyEntitiesByTraderId(traderEntity.getId());
        paymentsEntity.setParty(trader);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("PAYMENT");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);
        paymentsEntity.setTransactionGroup(transactionGroupEntity);

        paymentsEntity = paymentsDao.save(paymentsEntity);

        transactionService.applyPayment(paymentsEntity);
    }

}
