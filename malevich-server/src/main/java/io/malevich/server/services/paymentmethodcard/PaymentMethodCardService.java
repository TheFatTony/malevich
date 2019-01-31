package io.malevich.server.services.paymentmethodcard;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentMethodCardService {

    List<PaymentMethodCardEntity> findAll();

    PaymentMethodCardEntity save(PaymentMethodCardEntity paymentMethod);
}

