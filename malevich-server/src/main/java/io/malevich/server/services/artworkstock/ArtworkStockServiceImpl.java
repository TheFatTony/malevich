package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.TransactionGroupEntity;
import io.malevich.server.fabric.services.ComposerService;
import io.malevich.server.repositories.artworkstock.ArtworkStockDao;
import io.malevich.server.repositories.transactiongroup.TransactionGroupDao;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.transfer.FilterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ArtworkStockServiceImpl implements ArtworkStockService {

    @Autowired
    private TransactionGroupDao transactionGroupDao;

    @Autowired
    private ArtworkStockDao artworkStockDao;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private TransactionService transactionService;

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
        artworkStockEntity.setArtwork(artworkService.save(artworkStockEntity.getArtwork()));
        artworkStockEntity = this.artworkStockDao.save(artworkStockEntity);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("NEW_ART");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);

        transactionService.createArtworkStock(artworkStockEntity, transactionGroupEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        GalleryEntity gallery = galleryService.getCurrent();
        ArtworkStockEntity existing = artworkStockDao.getOne(id);

        if (existing == null || existing.getGallery().getId() != gallery.getId())
            return;

        artworkStockDao.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtworkStockEntity find(long id) {
        return artworkStockDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkStockEntity> filterStocks(Pageable pageable, FilterDto filterDto) {
        if (filterDto.getMinPrice() != 0 && filterDto.getMaxPrice() != 0 && filterDto.getCategoryId() != 0) {
            return artworkStockDao.filterByPriceAndCategory(pageable, filterDto.getMinPrice(), filterDto.getMaxPrice(), filterDto.getCategoryId());
        }
        if (filterDto.getMinPrice() != 0 && filterDto.getMaxPrice() != 0) {
            return artworkStockDao.filterByPrice(pageable, filterDto.getMinPrice(), filterDto.getMaxPrice());
        }
        if (filterDto.getCategoryId() != 0) {
            return artworkStockDao.filterByCategory(pageable, filterDto.getCategoryId());
        } else {
            return artworkStockDao.findAll(pageable);
        }
    }
}
