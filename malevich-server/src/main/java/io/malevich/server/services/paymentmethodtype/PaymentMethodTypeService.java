package io.malevich.server.services.paymentmethodtype;

import io.malevich.server.domain.PaymentMethodTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PaymentMethodTypeService {

    List<PaymentMethodTypeEntity> findAll();

    PaymentMethodTypeEntity getBitcoinType();
}
