package io.malevich.server.revolut.services.payments;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.CounterpartyModel;
import io.malevich.server.revolut.model.PaymentRequestModel;
import io.malevich.server.revolut.model.PaymentResponceModel;
import io.malevich.server.revolut.model.ReceiverModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.UUID;


@Slf4j
@Service
public class PaymentsBankServiceImpl extends GenericBankServiceImpl implements PaymentsBankService {

    @Value("${malevich.revolut.account.id}")
    protected String accountId;

    public PaymentsBankServiceImpl() {
        super("pay");
    }

    @Override
    public PaymentResponceModel create(PaymentsEntity entity) {
        PaymentRequestModel request = new PaymentRequestModel();

        request.setRequestId(UUID.randomUUID().toString());
        request.setAccountId(accountId);

        if (!(entity.getPaymentMethod() instanceof PaymentMethodAccountEntity))
            // todo throw exception
            return null;

        PaymentMethodAccountEntity paymentMethodAccountEntity = (PaymentMethodAccountEntity) entity.getPaymentMethod();

        ReceiverModel receiver = new ReceiverModel();
        receiver.setCounterpartyId(paymentMethodAccountEntity.getRevolutCounterpartyId());
        receiver.setAccountId(paymentMethodAccountEntity.getRevolutAccountId());

        request.setReceiver(receiver);

        request.setAmount(entity.getAmount().abs());
        request.setCurrency("EUR");

        return doPost(request, PaymentResponceModel.class);
    }


}
