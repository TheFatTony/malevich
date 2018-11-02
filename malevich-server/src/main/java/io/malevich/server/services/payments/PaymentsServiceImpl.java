package io.malevich.server.services.payments;

import io.malevich.server.repositories.payments.PaymentsDao;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.TraderEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

        paymentsEntity = paymentsDao.save(paymentsEntity);

        transactionService.addAccountStates(paymentsEntity);
    }

}
