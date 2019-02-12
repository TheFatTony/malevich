package io.malevich.server.services.document;

import io.malevich.server.domain.DocumentEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> findDocs();

    List<DocumentEntity> findByParticipantId(Long participantId);

    DocumentEntity trySave(DocumentEntity entity);

    DocumentEntity save(DocumentEntity entity);

    void delete(Long id);
}
