package io.malevich.server.services.gallery;


import io.malevich.server.domain.GalleryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GalleryService {

    List<GalleryEntity> findAll();

    GalleryEntity find(Long id);
}