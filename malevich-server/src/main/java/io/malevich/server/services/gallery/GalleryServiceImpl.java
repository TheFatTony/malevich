package io.malevich.server.services.gallery;


import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.repositories.gallery.GalleryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class GalleryServiceImpl implements GalleryService {


    @Autowired
    private GalleryDao galleryDao;

    protected GalleryServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryEntity> findAll() {
        return this.galleryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryEntity find(Long id) {
        return this.galleryDao.findById(id).get();
    }

}