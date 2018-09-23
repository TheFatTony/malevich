package io.malevich.server.services.gallery;


import io.malevich.server.dao.gallery.GalleryDao;
import io.malevich.server.entity.GalleryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class GalleryServiceImpl implements GalleryService {


    @Autowired
    private GalleryDao galleryDao;

    protected GalleryServiceImpl() {
    }

    @Override
    @Transactional
    public List<GalleryEntity> findAll() {
        return this.galleryDao.findAll();
    }

    @Override
    @Transactional
    public GalleryEntity find(Long id) {
        return this.galleryDao.find(id);
    }

}