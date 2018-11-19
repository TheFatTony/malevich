package io.malevich.server.repositories.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtworkStockDao extends JpaRepository<ArtworkStockEntity, Long> {

    @Query("select ase from ArtworkStockEntity ase where ase.artwork.category.id = :categoryId")
    Page<ArtworkStockEntity> filterByCategory(Pageable pageable, @Param("categoryId") long categoryId);

    @Query("select ase from ArtworkStockEntity ase where ase.artwork.estimatedPrice between :minPrice and :maxPrice")
    Page<ArtworkStockEntity> filterByPrice(Pageable pageable, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Query("select ase from ArtworkStockEntity ase where ase.artwork.category.id = :categoryId and ase.artwork.estimatedPrice between :minPrice and :maxPrice")
    Page<ArtworkStockEntity> filterByPriceAndCategory(Pageable pageable, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, @Param("categoryId") long categoryId);
}
