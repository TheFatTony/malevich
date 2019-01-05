package io.malevich.server.services.document;

import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.DocumentEntity;
import io.malevich.server.repositories.document.DocumentDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentEntity> findDocs() {
        CounterpartyEntity current = counterpartyService.getCurrent();
        return this.documentDao.findByCounterparty_Id(current.getId());
    }

    @Override
    @Transactional
    public DocumentEntity trySave(DocumentEntity entity) {
        CounterpartyEntity current = counterpartyService.getCurrent();
        entity.setCounterparty(current);
        entity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        delayedChangeService.saveEntity(entity);
        return entity;
    }

    @Override
    @Transactional
    public DocumentEntity save(DocumentEntity entity) {
        return this.documentDao.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CounterpartyEntity me = counterpartyService.getCurrent();
        DocumentEntity existing = documentDao.findById(id).orElse(null);

        if(existing == null || !existing.getCounterparty().getId().equals(me.getId()))
            return;

        this.documentDao.deleteById(id);
    }
}
