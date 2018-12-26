package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.*;
import io.malevich.server.fabric.services.ComposerService;
import io.malevich.server.repositories.artworkstock.ArtworkStockDao;
import io.malevich.server.repositories.transactiongroup.TransactionGroupDao;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
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
    private TransactionGroupDao transactionGroupDao;

    @Autowired
    private ArtworkStockDao artworkStockDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> findAll() {
        return this.artworkStockDao.findAll();
    }

    @Override
    @Transactional
    public void add(ArtworkStockEntity artworkStockEntity) {
        CounterpartyEntity counterpartyEntity = counterpartyService.getCurrent();

        if(counterpartyEntity == null || counterpartyEntity.getGallery() == null)
            return;

        GalleryEntity gallery = counterpartyEntity.getGallery();
        artworkStockEntity.setGallery(gallery);
        artworkStockEntity.setArtwork(artworkService.save(artworkStockEntity.getArtwork()));
        artworkStockEntity = this.artworkStockDao.save(artworkStockEntity);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("NEW_ART");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);


        CounterpartyEntity malevichEntity = counterpartyService.getMalevich();
        TransactionTypeEntity transactionTypeEntity = transactionTypeService.getCreateArtwork();

        transactionService.createTransactionAndReverse(transactionTypeEntity, transactionGroupEntity, counterpartyEntity, malevichEntity, artworkStockEntity, 0D, 1L);
    }

    @Override
    @Transactional
    public void delete(long id) {
        CounterpartyEntity counterpartyEntity = counterpartyService.getCurrent();

        if(counterpartyEntity == null || counterpartyEntity.getGallery() == null)
            return;

        GalleryEntity gallery = counterpartyEntity.getGallery();
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
    public Page<ArtworkStockEntity> findAll(Specification<ArtworkStockEntity> specification, Pageable pageable) {
        return artworkStockDao.findAll(specification, pageable);
    }

}
