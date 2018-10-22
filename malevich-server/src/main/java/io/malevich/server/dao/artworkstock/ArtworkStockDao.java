package io.malevich.server.dao.artworkstock;

import io.malevich.server.entity.ArtworkStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ArtworkStockDao extends JpaRepository<ArtworkStockEntity, Long> {

  List<ArtworkStockEntity> findAll();

}
