package io.malevich.server.services.paymentmethodtype;

import io.malevich.server.entity.PaymentMethodTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface PaymentMethodTypeService {

  List<PaymentMethodTypeEntity> findAll();

}
