package io.malevich.server.fabric.services.artworkstock;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
        artworkStockAsset.setDealCount(0);
        artworkStockAsset.setConfirmed(false);
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
            throw new RuntimeException(errorResponse);
        }
    }

    @Override
    public ArtworkStockAsset findOne(Long id) {
        try {
            ResponseEntity<ArtworkStockAsset> res = restTemplate.exchange(this.composerUrl + "/" + this.endpoint + "/{id}", HttpMethod.GET, null, new ParameterizedTypeReference<ArtworkStockAsset>() {
            }, id);
            return res.getBody();
        } catch (HttpStatusCodeException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatusCode().value())
                return null;

            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

}
