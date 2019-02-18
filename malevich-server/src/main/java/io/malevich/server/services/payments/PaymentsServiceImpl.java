package io.malevich.server.services.payments;

import io.malevich.server.domain.*;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.fabric.services.payment.PaymentTransactionService;
import io.malevich.server.repositories.payments.PaymentsDao;
import io.malevich.server.revolut.services.payments.PaymentsBankService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import io.malevich.server.util.PaymentFop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsDao paymentsDao;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentsBankService paymentsBankService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentsEntity> findOwnPayments() {
//        List<PaymentsEntity> paymentsEntities = new ArrayList<>();
//        List<PaymentTransaction> list = paymentTransactionService.list();
//        for (PaymentTransaction p: list) {
//            PaymentsEntity paymentsEntity = new PaymentsEntity();
//            paymentsEntity.setAmount(p.getAmount());
//            paymentsEntity.setPaymentType(
//                    paymentTypeService.getValues().get(p.getPaymentType()));
//            paymentsEntity.setEffectiveDate(p.getTimestamp());
//            paymentsEntity.setTransactionId(p.getTransactionId());
//
//            paymentsEntities.add(paymentsEntity);
//        }
//
//        return paymentsEntities;

        ParticipantEntity current = participantService.getCurrent();
        return this.paymentsDao.findAllByParticipant_Id(current.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentsEntity> findAllByParticipant(Long participantId) {
        return this.paymentsDao.findAllByParticipant_Id(participantId);
    }

    @Override
    @Transactional
    public void insertPayment(PaymentsEntity paymentsEntity) {
        paymentsEntity.setAmount(paymentsEntity.getAmount().abs());
        paymentsEntity.setPaymentType(paymentTypeService.getPaymentType());
        paymentsEntity.setPaymentMethod(paymentMethodService.findById(paymentsEntity.getPaymentMethod().getId()));

        paymentsEntity = paymentsDao.save(paymentsEntity);
        paymentTransactionService.create(paymentsEntity);
    }

    @Override
    @Transactional
    public void withdrawPayment(PaymentsEntity paymentsEntity) {
        paymentsEntity.setAmount(paymentsEntity.getAmount().abs().negate());
        paymentsEntity.setPaymentType(paymentTypeService.getWithdrawalType());
        paymentsEntity.setPaymentMethod(paymentMethodService.findById(paymentsEntity.getPaymentMethod().getId()));

        paymentsEntity.setParticipant(participantService.getCurrent());

        paymentsEntity = paymentsDao.save(paymentsEntity);

        if (paymentsEntity.getPaymentMethod() != null && paymentMethodTypeService.getAccountType().equals(paymentsEntity.getPaymentMethod().getType()))
            paymentsBankService.create(paymentsEntity);

        paymentTransactionService.create(paymentsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentsEntity getPayments(Long id) {
        ParticipantEntity currentParticicpant = participantService.getCurrent();
        PaymentsEntity paymentsEntity = this.paymentsDao.findById(id).orElse(null);

        if (!paymentsEntity.getParticipant().equals(currentParticicpant))
            return null;

        return paymentsEntity;
    }

    @Override
    public ResponseEntity<byte[]> createFop(PaymentsEntity entity) {
        String fileName = "receipt.pdf";
        return new PaymentFop().create(entity, fileName);
    }


}
