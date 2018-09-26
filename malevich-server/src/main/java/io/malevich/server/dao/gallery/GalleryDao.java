package io.malevich.server.dao.gallery;

import io.malevich.server.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryDao extends JpaRepository<GalleryEntity, Long> {

    @Query("select ge from GalleryEntity ge join fetch ge.organization join fetch ge.thumbnail join fetch ge.image")
    List<GalleryEntity> findAll();


}
