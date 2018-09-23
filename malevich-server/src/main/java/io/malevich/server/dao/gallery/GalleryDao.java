package io.malevich.server.dao.gallery;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.GalleryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GalleryDao extends Dao<GalleryEntity, Long> {

    List<GalleryEntity> findAll();

    GalleryEntity find(Long id);
}
