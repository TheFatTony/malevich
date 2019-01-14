package io.malevich.server.services.document;

import io.malevich.server.domain.DocumentEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> findDocs();

    DocumentEntity trySave(DocumentEntity entity);

    DocumentEntity save(DocumentEntity entity);

    void delete(Long id);
}
