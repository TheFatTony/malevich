package io.malevich.server.fabric.services.payment;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


@Slf4j
@Service
public class PaymentTransactionServiceImpl extends GenericComposerServiceImpl<PaymentsEntity> implements PaymentTransactionService {


    @Autowired
    private ParticipantService participantService;

    public PaymentTransactionServiceImpl() {
        super("Payment");
    }

    @Override
    public void create(PaymentsEntity entity) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setParty("resource:io.malevich.network.Trader#" + entity.getParty().getParticipant().getUser().getUsername());
        paymentTransaction.setAmount(entity.getAmount());
        paymentTransaction.setPaymentType(entity.getPaymentType().getId());

        doPost(paymentTransaction);
    }

    @Override
    public List<PaymentTransaction> list() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
             ResponseEntity<List<PaymentTransaction>> res = restTemplate.exchange(composerUrl + "/queries/selectPaymentsByCounterparty?party={party}", HttpMethod.GET, null, new ParameterizedTypeReference<List<PaymentTransaction>>() {}, ("resource:io.malevich.network.Trader#"+ participantEntity.getUser().getUsername()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }
}
