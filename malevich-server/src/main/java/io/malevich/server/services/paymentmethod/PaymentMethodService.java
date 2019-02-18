package io.malevich.server.services.paymentmethod;

import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface PaymentMethodService {

    List<PaymentMethodEntity> findAll();

    PaymentMethodEntity findById(Long paymentMethodId);

    PaymentMethodEntity save(PaymentMethodEntity paymentMethod);
}

