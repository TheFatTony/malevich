package io.malevich.server.repositories.gallery;

import io.malevich.server.domain.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryDao extends JpaRepository<GalleryEntity, Long> {

    @Query("select ge from GalleryEntity ge")
    List<GalleryEntity> findAll();

}
