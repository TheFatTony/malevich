package io.malevich.server.services.document.trader;

import io.malevich.server.domain.TraderDocumentEntity;

public interface TraderDocumentService {

    void save(TraderDocumentEntity entity);

    void deleteById(Long documentId, Long traderId);
}
