package io.malevich.server.fabric.services.artworkstock;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ArtworkStockAssetServiceImpl extends GenericComposerServiceImpl<ArtworkStockEntity> implements ArtworkStockAssetService {


    public ArtworkStockAssetServiceImpl() {
        super("ArtworkStock");
    }

    @Override
    public void create(ArtworkStockEntity entity) {
        ArtworkStockAsset artworkStockAsset = new ArtworkStockAsset();
        artworkStockAsset.setId(entity.getId().toString());
        artworkStockAsset.setArtwork("resource:io.malevich.network.Artwork#" + entity.getArtwork().getId().toString());
        artworkStockAsset.setHolder("resource:io.malevich.network.Gallery#" + entity.getGallery().getId().toString());
        artworkStockAsset.setOwner("resource:io.malevich.network.Gallery#" + entity.getGallery().getId().toString());

        doPost(artworkStockAsset);
    }
}
