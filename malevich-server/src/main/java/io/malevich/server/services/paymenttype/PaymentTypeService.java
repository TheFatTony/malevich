package io.malevich.server.services.paymenttype;

import io.malevich.server.domain.PaymentTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public interface PaymentTypeService {

  List<PaymentTypeEntity> findAll();

  PaymentTypeEntity getPaymentType();

  PaymentTypeEntity getWithdrawalType();

  Map<String, PaymentTypeEntity> getValues();
}
