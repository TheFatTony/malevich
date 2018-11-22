package io.malevich.server.services.paymenttype;

import io.malevich.server.domain.PaymentTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface PaymentTypeService {

  List<PaymentTypeEntity> findAll();

  PaymentTypeEntity getPaymentType();

  PaymentTypeEntity getWithdrawalType();

}
