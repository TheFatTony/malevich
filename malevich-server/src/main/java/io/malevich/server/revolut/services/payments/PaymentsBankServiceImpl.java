package io.malevich.server.revolut.services.payments;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.RevolutWithdrawalHistoryEntity;
import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.PaymentModel;
import io.malevich.server.revolut.model.PaymentRequestModel;
import io.malevich.server.revolut.model.ReceiverModel;
import io.malevich.server.services.revolutwithdrawalhistory.RevolutWithdrawalHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.sql.Timestamp;
import java.util.UUID;


@Slf4j
@Service
public class PaymentsBankServiceImpl extends GenericBankServiceImpl implements PaymentsBankService {

    @Value("${malevich.revolut.account.id}")
    protected String accountId;

    @Autowired
    private RevolutWithdrawalHistoryService revolutWithdrawalHistoryService;

    public PaymentsBankServiceImpl() {
        super("pay");
    }

    @Override
    @Transactional
    public PaymentModel create(PaymentsEntity entity) {
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

        PaymentModel paymentModel = doPost(request);

        RevolutWithdrawalHistoryEntity historyEntity = new RevolutWithdrawalHistoryEntity();
        historyEntity.setPayment(entity);
        historyEntity.setRequestId(request.getRequestId());
        historyEntity.setAccountId(request.getAccountId());
        historyEntity.setReceiverCounterpartyId(request.getReceiver().getCounterpartyId());
        historyEntity.setReceiverAccountId(request.getReceiver().getAccountId());
        historyEntity.setAmount(request.getAmount());
        historyEntity.setCurrency(request.getCurrency());
        historyEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));

        revolutWithdrawalHistoryService.save(historyEntity);

        return paymentModel;
    }

    private PaymentModel doPost(Object arg) {
        HttpEntity<Object> requestBody = getHttpEntity(arg);
        try {
            ResponseEntity<PaymentModel> res = restTemplate.exchange(bankUrl + "/" + endpoint, HttpMethod.POST, requestBody, new ParameterizedTypeReference<PaymentModel>() {});
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }


}
