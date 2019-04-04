package io.malevich.server.services.revoluttransaction;

import com.yinyang.core.server.services.auth.AuthService;
import io.malevich.server.domain.*;
import io.malevich.server.fabric.services.payment.PaymentTransactionService;
import io.malevich.server.repositories.revoluttransaction.RevolutTransactionDao;
import io.malevich.server.revolut.model.TransactionLegModel;
import io.malevich.server.revolut.model.TransactionModel;
import io.malevich.server.revolut.services.transactions.TransactionsBankService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.paymentmethoddepositreference.PaymentMethodDepositReferenceService;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.malevich.server.util.DateUtils.addDay;
import static io.malevich.server.util.DateUtils.getDateWithoutTime;


@Service
@Slf4j
public class RevolutTransactionServiceImpl implements RevolutTransactionService {

    private final Pattern pattern = Pattern.compile("([0-9A-Z]{4} [0-9A-Z]{4} [0-9A-Z]{4} [0-9A-Z]{4}) malevich\\.io");

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

    @Autowired
    private PaymentMethodDepositReferenceService paymentMethodDepositReferenceService;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private AuthService authService;

    public RevolutTransactionServiceImpl() {
    }

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

    private boolean namesMatch(String x, String y) {
        if (x == null || y == null)
            return false;

        return x.equalsIgnoreCase(y);
    }

    private boolean namesMatch(PaymentMethodEntity paymentMethod, TransactionModel transactionModel) {
        if (paymentMethod == null || transactionModel == null)
            return false;

        TransactionLegModel leg = transactionModel.getLegs().get(0);

        final String paymentFromText = "Payment from ";

        if (!leg.getDescription().startsWith(paymentFromText))
            return false;

        String nameInTransaction = leg.getDescription().substring(paymentFromText.length());

        if (paymentMethod.getParticipant() instanceof TraderPersonEntity) {
            PersonEntity person = ((TraderPersonEntity) paymentMethod.getParticipant()).getPerson();

            if (person == null)
                return false;

            return namesMatch(nameInTransaction, person.getLastName() + " " + person.getFirstName()) ||
                    namesMatch(nameInTransaction, person.getFirstName() + " " + person.getLastName());
        } else {
            OrganizationEntity organization = (paymentMethod.getParticipant() instanceof TraderOrganizationEntity)
                    ? ((TraderOrganizationEntity) paymentMethod.getParticipant()).getOrganization()
                    : ((GalleryEntity) paymentMethod.getParticipant()).getOrganization();

            if (organization == null)
                return false;

            return namesMatch(nameInTransaction, organization.getLegalNameMl().get("en"));
        }
    }

    @Override
    @Transactional
    public void processRevolutTopUpTransaction(TransactionModel transaction, boolean force) {
        RevolutTransactionEntity existingTransactionEntity = revolutTransactionService.findById(transaction.getId());

        if (existingTransactionEntity != null)
            return;

        if (!force) {
            DelayedChangeEntity existingDelayedChangeEntity =
                    delayedChangeService.findByTypeIdAndAndReferenceId("REVOLUT_DEPOSIT", transaction.getId());

            if (existingDelayedChangeEntity != null)
                return;
        }

        TransactionLegModel leg = transaction.getLegs().get(0);

        PaymentMethodDepositReferenceEntity paymentMethod = getPaymentMethodByReference(transaction.getReference());

        if (paymentMethod == null)
            return;

        if (!force && !namesMatch(paymentMethod, transaction)) {
            DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
            delayedChangeEntity.setPayload(transaction);
            delayedChangeEntity.setReferenceId(transaction.getId());
            delayedChangeEntity.setTypeId("REVOLUT_DEPOSIT");
            delayedChangeEntity.setUser(paymentMethod.getParticipant().getUsers().get(0));
            delayedChangeService.save(delayedChangeEntity);
            return;
        }

        PaymentsEntity paymentEntity = new PaymentsEntity();
        paymentEntity.setParticipant(paymentMethod.getParticipant());
        paymentEntity.setPaymentMethod(paymentMethod);
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

            processRevolutTopUpTransaction(transaction, false);
        }
    }

    private PaymentMethodDepositReferenceEntity getPaymentMethodByReference(String reference) {

        Matcher matcher = pattern.matcher(reference);

        if (!matcher.find())
            return null;

        String referenceToSearch = matcher.group(1);

        return paymentMethodDepositReferenceService.findByReference(referenceToSearch);
    }
}
