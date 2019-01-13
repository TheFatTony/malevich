package io.malevich.server.fabric.services.artwork;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.fabric.model.ArtworkAsset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ArtworkAssetServiceImpl extends GenericComposerServiceImpl<ArtworkEntity> implements ArtworkAssetService {


    public ArtworkAssetServiceImpl() {
        super("Artwork");
    }

    @Override
    public void create(ArtworkEntity entity) {
        ArtworkAsset artworkAsset = new ArtworkAsset();
        artworkAsset.setToken(entity.getId().toString());
        artworkAsset.setDescription(entity.getDescriptionMl().get("en"));
        artworkAsset.setEstimatedPrice(entity.getEstimatedPrice());

        doPost(artworkAsset);
    }
}
