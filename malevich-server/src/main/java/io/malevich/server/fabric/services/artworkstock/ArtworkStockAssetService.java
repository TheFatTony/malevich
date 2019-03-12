package io.malevich.server.fabric.services.artworkstock;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtworkStockAssetService extends GenericComposerService<ArtworkStockEntity> {

    List<ArtworkStockAsset> selectOwnedArtworkStocks();

    ArtworkStockAsset findOne(Long id);
}
