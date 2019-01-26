package io.malevich.server.repositories.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArtworkStockDao extends JpaRepository<ArtworkStockEntity, Long>, JpaSpecificationExecutor<ArtworkStockEntity> {

    List<ArtworkStockEntity> findAllByGallery_Id(Long galleryId);

}
