package io.malevich.server.services.tradehistory;

import io.malevich.server.fabric.model.TradeHistoryAsset;
import io.malevich.server.fabric.services.tradehistory.TradeHistoryAssetService;
import io.malevich.server.domain.TradeHistoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {


    @Autowired
    private TradeHistoryAssetService tradeHistoryAssetService;


    @Override
    @Transactional(readOnly = true)
    public List<TradeHistoryEntity> findAllByArtworkId(Long artworkId) {
        List<TradeHistoryEntity> result = new ArrayList<>();
        List<TradeHistoryAsset> list =  tradeHistoryAssetService.list(artworkId);

        for (TradeHistoryAsset asset: list){
            TradeHistoryEntity tradeHistoryEntity = new TradeHistoryEntity();
            tradeHistoryEntity.setAmount(asset.getBidOrder().getOrder().getAmount());
            tradeHistoryEntity.setEffectiveDate(asset.getEffectiveDate());

            result.add(tradeHistoryEntity);
        }

        return result;
    }

}
