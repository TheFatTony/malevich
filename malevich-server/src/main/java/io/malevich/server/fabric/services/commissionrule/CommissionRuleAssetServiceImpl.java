package io.malevich.server.fabric.services.commissionrule;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.CommissionRuleEntity;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.fabric.model.CommissionRuleAsset;
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
public class CommissionRuleAssetServiceImpl extends GenericComposerServiceImpl<CommissionRuleEntity> implements CommissionRuleAssetService {

    @Autowired
    private FabricObjectsService fabricObjectsService;

    public CommissionRuleAssetServiceImpl() {
        super("CommissionRule");
    }

    @Override
    public void create(CommissionRuleEntity entity) {
        CommissionRuleAsset commissionRuleAsset = new CommissionRuleAsset();
        commissionRuleAsset.setName(entity.getName());
        commissionRuleAsset.setValue(entity.getValue().doubleValue());

        doPost(commissionRuleAsset);

        FabricObjectsEntity fabricObjectsEntity = new FabricObjectsEntity();
        fabricObjectsEntity.setReferenceId(entity.getId().toString());
        fabricObjectsEntity.setTypeId("CommissionRule");
        fabricObjectsEntity.setPayload(entity);

        fabricObjectsService.save(fabricObjectsEntity);
    }


}
