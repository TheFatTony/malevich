package io.malevich.server.repositories.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArtworkStockDao extends JpaRepository<ArtworkStockEntity, Long> {

    List<ArtworkStockEntity> findAll();

}
