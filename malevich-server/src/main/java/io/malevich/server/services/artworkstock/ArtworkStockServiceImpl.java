package io.malevich.server.services.artworkstock;

import io.malevich.server.dao.artworkstock.ArtworkStockDao;
import io.malevich.server.entity.ArtworkStockEntity;
import io.malevich.server.entity.GalleryEntity;
import io.malevich.server.services.gallery.GalleryService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ArtworkStockServiceImpl implements ArtworkStockService {

    @Autowired
    private ArtworkStockDao artworkStockDao;

    @Autowired
    private GalleryService galleryService;

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> findAll() {
        return this.artworkStockDao.findAll();
    }

    @Override
    @Transactional
    public void add(ArtworkStockEntity artworkStockEntity) {
        GalleryEntity gallery = galleryService.getCurrent();
        artworkStockEntity.setGallery(gallery);
        this.artworkStockDao.save(artworkStockEntity);
    }

}
