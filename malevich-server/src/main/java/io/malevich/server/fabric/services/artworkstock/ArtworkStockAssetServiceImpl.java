package io.malevich.server.fabric.services.artworkstock;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ArtworkStockAssetServiceImpl extends GenericComposerServiceImpl<ArtworkStockEntity> implements ArtworkStockAssetService {

    @Autowired
    private ParticipantService participantService;


    public ArtworkStockAssetServiceImpl() {
        super("ArtworkStock");
    }

    @Override
    public void create(ArtworkStockEntity entity) {
        ArtworkStockAsset artworkStockAsset = new ArtworkStockAsset();
        artworkStockAsset.setId(entity.getId().toString());
        artworkStockAsset.setToken(entity.getArtwork().getId().toString());
        artworkStockAsset.setCurrentAsk(0D);
        artworkStockAsset.setLastPrice(0D);
        artworkStockAsset.setOwner("resource:io.malevich.network.Gallery#" + entity.getGallery().getId().toString());
        artworkStockAsset.setHolder("resource:io.malevich.network.Gallery#" + entity.getGallery().getId().toString());

        doPost(artworkStockAsset);
    }

    @Override
    public List<ArtworkStockAsset> selectOwnedArtworkStocks() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        if (participantEntity == null)
            return new ArrayList<>();

        String fabricClass = null;

        if ("G".equals(participantEntity.getType().getId())) {
            fabricClass = "resource:io.malevich.network.Gallery#";
        } else {
            fabricClass = "resource:io.malevich.network.Trader#";
        }

        try {
            ResponseEntity<List<ArtworkStockAsset>> res = restTemplate.exchange(composerUrl + "/queries/getOwnedArtworkStocks?owner={owner}", HttpMethod.GET, null, new ParameterizedTypeReference<List<ArtworkStockAsset>>() {
            }, (fabricClass + participantEntity.getId()));
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();

            String prettyError = errorResponse.substring(errorResponse.indexOf("!#{") + 3, errorResponse.indexOf("}#!"));
            if (prettyError == null)
                throw new RuntimeException(errorResponse);
            else
                throw new RuntimeException(prettyError);
        }
    }

}
