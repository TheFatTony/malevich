package io.malevich.server.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface PaymentsService {

    List<PaymentsEntity> findOwnPayments();

    List<PaymentsEntity> findAllByParticipant(Long participantId);

    void insertPayment(PaymentsEntity paymentsEntity);

    PaymentsEntity getPayments(Long id);

    ResponseEntity<byte[]> createFop(PaymentsEntity entity);

    void withdrawPayment(PaymentsEntity paymentsEntity);
}
