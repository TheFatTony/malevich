package io.malevich.server.fabric.services.cancelorder;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.OrderConcept;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.services.fabricobjects.FabricObjectsService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
public class CancelOrderTransactionServiceImpl extends GenericComposerServiceImpl<OrderEntity> implements CancelOrderTransactionService {


    @Autowired
    private FabricObjectsService fabricObjectsService;

    public CancelOrderTransactionServiceImpl() {
        super("CancelOrder");
    }

    @Override
    public void create(OrderEntity entity) {
        String fabricClass = null;

        if ("G".equals(entity.getParticipant().getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }

        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setOrder(new OrderConcept());


        orderTransaction.getOrder().setId(entity.getId());
        orderTransaction.getOrder().setAmount(entity.getAmount());
        orderTransaction.getOrder().setOrderType(entity.getType().getId());
        orderTransaction.getOrder().setOrderStatus(entity.getStatus().getId());
        orderTransaction.getOrder().setArtworkStock("resource:io.malevich.network.ArtworkStock#" + entity.getArtworkStock().getId().toString());
        orderTransaction.getOrder().setCounterparty(fabricClass + entity.getParticipant().getId());

        doPost(orderTransaction);
    }


}
