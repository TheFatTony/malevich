package io.malevich.server.fabric.services.tradehistory;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.fabric.model.TradeHistoryAsset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeHistoryAssetService extends GenericComposerService<TradeHistoryEntity> {

    List<TradeHistoryAsset> list(Long artworkId);
}
