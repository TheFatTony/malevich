package io.malevich.server.fabric.services.balancehistory;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.fabric.model.BalanceHistoryAsset;
import io.malevich.server.fabric.model.TradeHistoryAsset;
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
public class BalanceHistoryAssetServiceImpl extends GenericComposerServiceImpl implements BalanceHistoryAssetService {

    @Autowired
    private ParticipantService participantService;

    public BalanceHistoryAssetServiceImpl() {
        super("BalanceHistory");
    }


    @Override
    public List<BalanceHistoryAsset> list() {

        ParticipantEntity participantEntity = participantService.getCurrent();
        String fabricClass = null;

        if ("G".equals(participantEntity.getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }

        try {
            ResponseEntity<List<BalanceHistoryAsset>> res = restTemplate.exchange(composerUrl + "/queries/getBalanceHistoryByCounterparty?counterparty={counterparty}", HttpMethod.GET, null, new ParameterizedTypeReference<List<BalanceHistoryAsset>>() {
            }, (fabricClass + participantEntity.getId()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

    @Override
    public void create(Object o) {
        new Exception("Not allowed");
    }
}
