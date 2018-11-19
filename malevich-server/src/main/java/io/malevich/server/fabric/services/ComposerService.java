package io.malevich.server.fabric.services;

import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComposerService {

     void addArtwork(ArtworkStockEntity artworkStockEntity);

}
