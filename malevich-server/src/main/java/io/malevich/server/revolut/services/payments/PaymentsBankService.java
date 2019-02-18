package io.malevich.server.revolut.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.revolut.GenericBankService;
import io.malevich.server.revolut.model.PaymentResponceModel;
import org.springframework.stereotype.Service;

@Service
public interface PaymentsBankService extends GenericBankService {

    PaymentResponceModel create(PaymentsEntity entity);
}
