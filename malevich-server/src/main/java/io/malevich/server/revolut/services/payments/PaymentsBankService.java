package io.malevich.server.revolut.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.revolut.GenericBankService;
import io.malevich.server.revolut.model.PaymentModel;
import org.springframework.stereotype.Service;

@Service
public interface PaymentsBankService extends GenericBankService {

    PaymentModel create(PaymentsEntity entity);
}
