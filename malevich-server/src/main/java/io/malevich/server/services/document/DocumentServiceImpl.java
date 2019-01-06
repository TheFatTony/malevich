package io.malevich.server.services.document;

import io.malevich.server.domain.*;
import io.malevich.server.repositories.document.DocumentDao;
import io.malevich.server.services.document.gallery.GalleryDocumentService;
import io.malevich.server.services.document.trader.TraderDocumentService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.trader.TraderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private GalleryDocumentService galleryDocumentService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private TraderService traderService;

    @Autowired
    private TraderDocumentService traderDocumentService;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentEntity> findTraderDocs() {
        TraderOrganizationEntity traderEntity = traderService.getCurrentTrader();
        return this.documentDao.findTraderDocs(traderEntity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentEntity> findGalleryDocs() {
        GalleryEntity galleryEntity = galleryService.getCurrent();
        return this.documentDao.findGalleryDocs(galleryEntity.getId());
    }

    @Override
    @Transactional
    public DocumentEntity save(DocumentEntity entity) {
        return this.documentDao.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GalleryEntity galleryEntity = galleryService.getCurrent();
        TraderOrganizationEntity traderEntity = traderService.getCurrentTrader();
        if (galleryEntity != null && id != null) {
            this.galleryDocumentService.deleteById(id, galleryEntity.getId());
        }
        if (traderEntity != null && id != null) {
            this.traderDocumentService.deleteById(id, traderEntity.getId());
        }
        this.documentDao.deleteById(id);
    }

    @Override
    public void userDocs(DocumentEntity documentEntity) {

        GalleryEntity galleryEntity = galleryService.getCurrent();
        TraderOrganizationEntity traderEntity = traderService.getCurrentTrader();
        if (galleryEntity != null && documentEntity != null) {
            GalleryDocumentEntity galleryDocumentEntity = new GalleryDocumentEntity();
            galleryDocumentEntity.setDocument(documentEntity);
            galleryDocumentEntity.setGallery(galleryEntity);
            this.galleryDocumentService.save(galleryDocumentEntity);
        }
        if (traderEntity != null && documentEntity != null) {
            TraderDocumentEntity traderDocumentEntity = new TraderDocumentEntity();
            traderDocumentEntity.setDocument(documentEntity);
            traderDocumentEntity.setTrader(traderEntity);
            this.traderDocumentService.save(traderDocumentEntity);
        }
    }
}
