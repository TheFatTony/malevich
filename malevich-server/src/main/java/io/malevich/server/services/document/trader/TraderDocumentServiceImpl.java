package io.malevich.server.services.document.trader;

import io.malevich.server.domain.TraderDocumentEntity;
import io.malevich.server.repositories.document.trader.TraderDocumentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TraderDocumentServiceImpl implements TraderDocumentService {

    @Autowired
    private TraderDocumentDao documentDao;

    @Override
    @Transactional
    public void save(TraderDocumentEntity entity) {
        this.documentDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long documentId, Long traderId) {
        this.documentDao.deleteById(documentId, traderId);
    }
}
