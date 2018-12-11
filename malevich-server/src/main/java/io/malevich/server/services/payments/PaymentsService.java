package io.malevich.server.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PaymentsService {

    List<PaymentsEntity> findAll();

    void insertPayment(PaymentsEntity paymentsEntity);

    PaymentsEntity findById(Long id);

}
