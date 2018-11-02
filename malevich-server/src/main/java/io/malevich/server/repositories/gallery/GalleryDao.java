package io.malevich.server.repositories.gallery;

import io.malevich.server.domain.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryDao extends JpaRepository<GalleryEntity, Long> {

    @Query("select ge from GalleryEntity ge join fetch ge.organization join fetch ge.thumbnail join fetch ge.image")
    List<GalleryEntity> findAll();

//    @Query("select te from GalleryEntity te join fetch te.user where te.user.name = :name")
//    Optional<GalleryEntity> findByUserName(@Param(value = "name") String name);

    Optional<GalleryEntity> findByUsers_Name(String name);

}
