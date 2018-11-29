package io.malevich.server.services.document.gallery;

import io.malevich.server.domain.GalleryDocumentEntity;
import io.malevich.server.repositories.document.gallery.GalleryDocumentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GalleryDocumentServiceImpl implements GalleryDocumentService {

    @Autowired
    private GalleryDocumentDao documentDao;

    @Override
    @Transactional
    public GalleryDocumentEntity save(GalleryDocumentEntity entity) {
        return this.documentDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long documentId, Long galleryId) {
        this.documentDao.deleteById(documentId, galleryId);
    }
}
