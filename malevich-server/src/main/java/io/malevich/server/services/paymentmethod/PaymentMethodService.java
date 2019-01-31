package io.malevich.server.services.paymentmethod;

import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PaymentMethodService {

    List<PaymentMethodEntity> findAll();

    PaymentMethodEntity save(PaymentMethodEntity paymentMethod);
}
