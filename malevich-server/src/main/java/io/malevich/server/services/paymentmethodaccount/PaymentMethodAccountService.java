package io.malevich.server.services.paymentmethodaccount;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentMethodAccountService {

    List<PaymentMethodAccountEntity> findAll();

    PaymentMethodAccountEntity save(PaymentMethodAccountEntity paymentMethod);
}
