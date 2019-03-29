package io.malevich.server.fabric.services.payment;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.services.fabricobjects.FabricObjectsService;
import io.malevich.server.services.participant.ParticipantService;
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
    private FabricObjectsService fabricObjectsService;


    public PaymentTransactionServiceImpl() {
        super("Payment");
    }

    @Override
    public void create(PaymentsEntity entity) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();

        String fabricClass = null;
        if ("G".equals(entity.getParticipant().getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }

        paymentTransaction.setParty(fabricClass + entity.getParticipant().getId());
        paymentTransaction.setAmount(Math.abs(entity.getAmount().doubleValue()));
        paymentTransaction.setPaymentType(entity.getPaymentType().getId());

        doPost(paymentTransaction);

        FabricObjectsEntity fabricObjectsEntity = new FabricObjectsEntity();
        fabricObjectsEntity.setReferenceId(entity.getId().toString());
        fabricObjectsEntity.setTypeId("Payment");
        fabricObjectsEntity.setPayload(entity);

        fabricObjectsService.save(fabricObjectsEntity);
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
            ResponseEntity<List<PaymentTransaction>> res = restTemplate.exchange(composerUrl + "/queries/getPaymentsByCounterparty?party={party}", HttpMethod.GET, null, new ParameterizedTypeReference<List<PaymentTransaction>>() {
            }, (fabricClass + participantEntity.getId()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }
}
