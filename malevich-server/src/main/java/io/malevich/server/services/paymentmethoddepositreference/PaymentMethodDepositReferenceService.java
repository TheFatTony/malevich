package io.malevich.server.services.paymentmethoddepositreference;

import io.malevich.server.domain.PaymentMethodDepositReferenceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PaymentMethodDepositReferenceService {

    List<PaymentMethodDepositReferenceEntity> findAll();

    PaymentMethodDepositReferenceEntity getOrCreate();

    PaymentMethodDepositReferenceEntity findByReference(String reference);
}
