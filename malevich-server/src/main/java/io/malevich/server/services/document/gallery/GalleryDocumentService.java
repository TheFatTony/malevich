package io.malevich.server.services.document.gallery;

import io.malevich.server.domain.GalleryDocumentEntity;

public interface GalleryDocumentService {

    GalleryDocumentEntity save(GalleryDocumentEntity entity);

    void deleteById(Long documentId, Long galleryId);
}
