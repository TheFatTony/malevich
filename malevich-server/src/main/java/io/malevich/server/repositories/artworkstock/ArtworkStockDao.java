package io.malevich.server.repositories.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtworkStockDao extends JpaRepository<ArtworkStockEntity, Long>, JpaSpecificationExecutor<ArtworkStockEntity> {

}
