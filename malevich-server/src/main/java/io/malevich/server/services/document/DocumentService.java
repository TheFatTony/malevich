package io.malevich.server.services.document;

import io.malevich.server.domain.DocumentEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> findTraderDocs();

    List<DocumentEntity> findGalleryDocs();

    DocumentEntity save(DocumentEntity entity);

    void delete(Long id);

    void userDocs(DocumentEntity entity);
}
