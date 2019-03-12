package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.fabric.services.artworkstock.ArtworkStockAssetService;
import io.malevich.server.repositories.artworkstock.ArtworkStockDao;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private ParticipantService participantService;

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> findAll() {
        return this.artworkStockDao.findAll();
    }

    @Override
    @Transactional
    public void add(ArtworkStockEntity artworkStockEntity) {
        ArtworkStockEntity entity = save(artworkStockEntity);
        artworkStockAssetService.create(entity);
    }

    @Override
    @Transactional
    public ArtworkStockEntity save(ArtworkStockEntity artworkStockEntity) {
        GalleryEntity gallery = galleryService.getCurrent();

        artworkStockEntity.setGallery(gallery);
        artworkStockEntity.setArtwork(artworkService.save(artworkStockEntity.getArtwork()));
        return this.artworkStockDao.save(artworkStockEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> getStoredArtworks() {
        ParticipantEntity counterpartyEntity = participantService.getCurrent();
        return findAllByGalleryId(counterpartyEntity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> getOwnArtworks() {
        List<ArtworkStockEntity> result = new ArrayList<>();

        for (ArtworkStockAsset asset : artworkStockAssetService.selectOwnedArtworkStocks()) {
            Long id = Long.parseLong(asset.getId().replace("resource:io.malevich.network.ArtworkStock#", ""));

            ArtworkStockEntity artworkStockEntity = find(id);
            artworkStockEntity.setInstantPrice(asset.getCurrentAsk());
            artworkStockEntity.setLastPrice(asset.getLastPrice());

            result.add(artworkStockEntity);
        }

        return result;
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
    @Transactional
    public void sync(long id){
        ArtworkStockAsset asset = artworkStockAssetService.findOne(id);

        if(asset == null)
            return;

        ArtworkStockEntity artworkStockEntity = find(id);

        if(artworkStockEntity != null){
            artworkStockEntity.setInstantPrice(asset.getCurrentAsk());
            artworkStockEntity.setLastPrice(asset.getLastPrice());
            this.artworkStockDao.save(artworkStockEntity);
        } else {
            // todo create?
            return;
        }
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
