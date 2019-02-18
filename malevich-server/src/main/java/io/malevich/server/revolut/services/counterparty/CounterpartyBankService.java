package io.malevich.server.revolut.services.counterparty;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.revolut.GenericBankService;
import io.malevich.server.revolut.model.CounterpartyModel;
import org.springframework.stereotype.Service;

@Service
public interface CounterpartyBankService extends GenericBankService {

    CounterpartyModel create(PaymentMethodAccountEntity entity);
}
