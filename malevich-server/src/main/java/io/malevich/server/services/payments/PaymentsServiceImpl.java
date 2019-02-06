package io.malevich.server.services.payments;

import io.malevich.server.domain.*;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.fabric.services.payment.PaymentTransactionService;
import io.malevich.server.repositories.payments.PaymentsDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import io.malevich.server.util.PaymentFop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private PaymentTransactionService paymentTransactionService;

    @Autowired
    private ParticipantService participantService;


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
        PaymentTypeEntity paymentType;

        if (paymentsEntity.getAmount() < 0) {
            paymentType = paymentTypeService.getWithdrawalType();
        } else {
            paymentType = paymentTypeService.getPaymentType();
        }

        paymentsEntity.setPaymentType(paymentType);

        paymentsEntity = paymentsDao.save(paymentsEntity);

        paymentTransactionService.create(paymentsEntity);

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
