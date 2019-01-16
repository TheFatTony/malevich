package io.malevich.server.fabric.services.payment;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;


@Slf4j
@Service
public class PaymentTransactionServiceImpl extends GenericComposerServiceImpl<PaymentsEntity> implements PaymentTransactionService {


    @Autowired
    private ParticipantService participantService;


    @Autowired
    private PaymentTypeService paymentTypeService;

    public PaymentTransactionServiceImpl() {
        super("Payment");
    }

    @Override
    public void create(PaymentsEntity entity) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setParty("resource:io.malevich.network.Trader#" + entity.getParticipant().getUser().getUsername());
        paymentTransaction.setAmount(entity.getAmount());
        paymentTransaction.setPaymentType(entity.getPaymentType().getId());

        doPost(paymentTransaction);
    }

    @Override
    public List<PaymentTransaction> list() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        String fabricClass = null;

        if ("G".equals(participantEntity.getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }


        try {
            ResponseEntity<List<PaymentTransaction>> res = restTemplate.exchange(composerUrl + "/queries/selectPaymentsByCounterparty?party={party}", HttpMethod.GET, null, new ParameterizedTypeReference<List<PaymentTransaction>>() {
            }, (fabricClass + participantEntity.getUser().getUsername()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }
}
