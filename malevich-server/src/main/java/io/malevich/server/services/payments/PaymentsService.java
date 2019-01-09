package io.malevich.server.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PaymentsService {

    List<PaymentsEntity> findOwnPayments();

    void insertPayment(PaymentsEntity paymentsEntity);

    PaymentsEntity getPayments(Long id);

    ResponseEntity<byte[]> createFop(PaymentsEntity entity);
}
