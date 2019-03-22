package io.malevich.server.fabric.services.balancehistory;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.fabric.model.BalanceHistoryAsset;
import io.malevich.server.fabric.model.TradeHistoryAsset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BalanceHistoryAssetService extends GenericComposerService {

    List<BalanceHistoryAsset> list();
}
