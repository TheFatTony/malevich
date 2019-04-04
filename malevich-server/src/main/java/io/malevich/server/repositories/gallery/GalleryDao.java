package io.malevich.server.repositories.gallery;

import io.malevich.server.domain.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryDao extends JpaRepository<GalleryEntity, Long> {

    @Query("select ge from GalleryEntity ge " +
            "left join fetch ge.image " +
            "left join fetch ge.thumbnail " +
            "left join fetch ge.country " +
            "left join fetch ge.addresses " +
            "left join fetch ge.kycLevel " +
            "left join fetch ge.type " +
            "left join fetch ge.organization ")
    List<GalleryEntity> findAll();

}