package io.malevich.server.fabric.services.commissionrule;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.CommissionRuleEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommissionRuleAssetService extends GenericComposerService<CommissionRuleEntity> {

}
