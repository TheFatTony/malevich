package io.malevich.server.services.revoluttransaction;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.RevolutTransactionEntity;
import io.malevich.server.fabric.services.payment.PaymentTransactionService;
import io.malevich.server.repositories.revoluttransaction.RevolutTransactionDao;
import io.malevich.server.revolut.model.TransactionLegModel;
import io.malevich.server.revolut.model.TransactionModel;
import io.malevich.server.revolut.services.transactions.TransactionsBankService;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static io.malevich.server.util.DateUtils.addDay;
import static io.malevich.server.util.DateUtils.getDateWithoutTime;


@Service
@Slf4j
public class RevolutTransactionServiceImpl implements RevolutTransactionService {

    @Autowired
    private RevolutTransactionDao revolutTransactionDao;

    @Autowired
    private TransactionsBankService transactionsBankService;

    @Autowired
    private RevolutTransactionService revolutTransactionService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    @Override
    @Transactional
    public RevolutTransactionEntity save(RevolutTransactionEntity entity) {
        return revolutTransactionDao.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public RevolutTransactionEntity findById(String id) {
        return revolutTransactionDao.findById(id).orElse(null);
    }

    private ParticipantEntity getParticipantByReference(String reference) {
        return null;
    }

    private void processRevolutTopUpTransaction(TransactionModel transaction) {
        TransactionLegModel leg = transaction.getLegs().get(0);

        ParticipantEntity participantEntity = getParticipantByReference(transaction.getReference());

        if (participantEntity == null)
            return;

        PaymentsEntity paymentEntity = new PaymentsEntity();
        paymentEntity.setParticipant(participantEntity);
        paymentEntity.setPaymentMethod(null); // get payment method
        paymentEntity.setAmount(leg.getAmount());
        paymentEntity.setPaymentType(paymentTypeService.getPaymentType());

        paymentEntity = paymentsService.save(paymentEntity);

        RevolutTransactionEntity transactionEntity = new RevolutTransactionEntity();
        transactionEntity.setId(transaction.getId());
        transactionEntity.setPayment(paymentEntity);
        transactionEntity.setType(transaction.getType());
        transactionEntity.setState(transaction.getState());
        transactionEntity.setReference(transaction.getReference());
        transactionEntity.setAccountId(leg.getAccountId());
        transactionEntity.setAmount(leg.getAmount());
        transactionEntity.setCurrency(leg.getCurrency());
        transactionEntity.setDescription(leg.getDescription());

        revolutTransactionService.save(transactionEntity);

        paymentTransactionService.create(paymentEntity);
    }

    @Override
    @Transactional
    public void processDeposits() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp today = getDateWithoutTime(now);
        Timestamp yesterday = addDay(today, -1);
        Timestamp tomorrow = addDay(today, 1);
        List<TransactionModel> transactions = transactionsBankService.getTransactions(yesterday, tomorrow);

        for (TransactionModel transaction : transactions) {
            if (!"topup".equals(transaction.getType()) || !"completed".equals(transaction.getState()))
                continue;

            TransactionLegModel leg = transaction.getLegs().stream().findFirst().orElse(null);

            //incomes only
            if (leg == null || leg.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0)
                continue;

            // EUR only!
            if (!"EUR".equals(leg.getCurrency()))
                continue;

            RevolutTransactionEntity transactionEntity = revolutTransactionService.findById(transaction.getId());

            if (transactionEntity != null)
                continue;

            processRevolutTopUpTransaction(transaction);
        }
    }
}
