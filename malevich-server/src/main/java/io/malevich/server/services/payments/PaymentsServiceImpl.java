package io.malevich.server.services.payments;

import io.malevich.server.domain.*;
import io.malevich.server.repositories.payments.PaymentsDao;
import io.malevich.server.repositories.transactiongroup.TransactionGroupDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import io.malevich.server.util.PaymentFop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private TransactionTypeService transactionTypeService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TransactionGroupDao transactionGroupDao;

    @Autowired
    private PaymentTypeService paymentTypeService;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentsEntity> findOwnPayments() {
        CounterpartyEntity entity = counterpartyService.getCurrent();
        return this.paymentsDao.findPaymentsEntityByParty_Id(entity.getId());
    }

    @Override
    @Transactional
    public void insertPayment(PaymentsEntity paymentsEntity) {
        CounterpartyEntity current = counterpartyService.getCurrent();
        CounterpartyEntity malevich = counterpartyService.getMalevich();

        PaymentTypeEntity paymentType;
        TransactionTypeEntity transactionType;

        if (paymentsEntity.getAmount() < 0) {
            paymentType = paymentTypeService.getWithdrawalType();
            transactionType = transactionTypeService.getWithdrawBalance();
        } else {
            paymentType = paymentTypeService.getPaymentType();
            transactionType = transactionTypeService.getAddBalance();
        }

        paymentsEntity.setParty(current);
        paymentsEntity.setPaymentType(paymentType);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("PAYMENT");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);

        paymentsEntity.setTransactionGroup(transactionGroupEntity);

        paymentsEntity = paymentsDao.save(paymentsEntity);

        transactionService.createTransactionAndReverse(transactionType, paymentsEntity.getTransactionGroup(), paymentsEntity.getParty(), malevich, null, paymentsEntity.getAmount(), 0L);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentsEntity getPayments(Long id) {
        return this.paymentsDao.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<byte[]> createFop(PaymentsEntity entity) {
        String fileName = "receipt.pdf";
        return new PaymentFop().create(entity, fileName);
    }
}
