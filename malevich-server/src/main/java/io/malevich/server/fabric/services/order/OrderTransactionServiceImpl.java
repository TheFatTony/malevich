package io.malevich.server.fabric.services.order;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.OrderConcept;
import io.malevich.server.fabric.model.OrderTransaction;
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
public class OrderTransactionServiceImpl extends GenericComposerServiceImpl<OrderEntity> implements OrderTransactionService {


    @Autowired
    private ParticipantService participantService;


    public OrderTransactionServiceImpl() {
        super("Order");
    }

    @Override
    public void create(OrderEntity entity) {
        ParticipantEntity participantEntity = entity.getParty().getParticipant();
        String fabricClass = null;

        if ("G".equals(participantEntity.getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }

        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setOrder(new OrderConcept());
        orderTransaction.getOrder().setId(entity.getId().toString());
        orderTransaction.getOrder().setAmount(entity.getAmount());
        orderTransaction.getOrder().setOrderType(entity.getType().getId());
        orderTransaction.getOrder().setOrderStatus(entity.getStatus().getId());
        orderTransaction.getOrder().setArtworkStock("resource:io.malevich.network.ArtworkStock#" + entity.getArtworkStock().getId().toString());
        orderTransaction.getOrder().setСounterparty(fabricClass + participantEntity.getId());

        doPost(orderTransaction);
    }

    @Override
    public List<OrderTransaction> list() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        String fabricClass = null;

        if ("G".equals(participantEntity.getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }


        try {
            ResponseEntity<List<OrderTransaction>> res = restTemplate.exchange(composerUrl + "/queries/selectPaymentsByCounterparty?party={party}", HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderTransaction>>() {
            }, (fabricClass + participantEntity.getUser().getUsername()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }

}
