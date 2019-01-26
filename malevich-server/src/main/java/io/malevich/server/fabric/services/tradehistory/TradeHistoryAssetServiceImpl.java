package io.malevich.server.fabric.services.tradehistory;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.fabric.model.TradeHistoryAsset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;


@Slf4j
@Service
public class TradeHistoryAssetServiceImpl extends GenericComposerServiceImpl<TradeHistoryEntity> implements TradeHistoryAssetService {


    public TradeHistoryAssetServiceImpl() {
        super("TradeHistory");
    }


    @Override
    public List<TradeHistoryAsset> list(Long artworkId) {

        String fabricClass = "resource:io.malevich.network.ArtworkStock#";

        try {
            ResponseEntity<List<TradeHistoryAsset>> res = restTemplate.exchange(composerUrl + "/queries/getTradeHistoryByArtworkStock?artworkStock={artworkStock}", HttpMethod.GET, null, new ParameterizedTypeReference<List<TradeHistoryAsset>>() {
            }, (fabricClass + artworkId));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }

    @Override
    public void create(TradeHistoryEntity tradeHistoryEntity) {
        new Exception("Not allowed");
    }
}
