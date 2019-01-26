package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.fabric.services.artworkstock.ArtworkStockAssetService;
import io.malevich.server.repositories.artworkstock.ArtworkStockDao;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.gallery.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ArtworkStockServiceImpl implements ArtworkStockService {


    @Autowired
    private ArtworkStockDao artworkStockDao;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private ArtworkStockAssetService artworkStockAssetService;

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> findAll() {
        return this.artworkStockDao.findAll();
    }

    @Override
    @Transactional
    public void add(ArtworkStockEntity artworkStockEntity) {
        GalleryEntity gallery = galleryService.getCurrent();

        if (gallery == null)
            return;

        artworkStockEntity.setGallery(gallery);
        artworkStockEntity.setArtwork(artworkService.save(artworkStockEntity.getArtwork()));
        artworkStockEntity = this.artworkStockDao.save(artworkStockEntity);

        artworkStockAssetService.create(artworkStockEntity);

    }

    @Override
    @Transactional
    public void delete(long id) {
        GalleryEntity gallery = galleryService.getCurrent();

        if (gallery == null)
            return;

        ArtworkStockEntity existing = artworkStockDao.findById(id).orElse(null);

        if (existing == null || existing.getGallery().getId() != gallery.getId())
            return;

        artworkStockDao.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtworkStockEntity find(long id) {
        return artworkStockDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkStockEntity> findAll(Specification<ArtworkStockEntity> specification, Pageable pageable) {
        return artworkStockDao.findAll(specification, pageable);
    }

    @Override
    public List<ArtworkStockEntity> findAllByGalleryId(Long galleryId) {
        return artworkStockDao.findAllByGallery_Id(galleryId);
    }

}
