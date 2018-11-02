package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ArtworkStockService {

    List<ArtworkStockEntity> findAll();

    void add(ArtworkStockEntity artworkStockEntity);

    void delete(long id);

    ArtworkStockEntity find(long id);
}
