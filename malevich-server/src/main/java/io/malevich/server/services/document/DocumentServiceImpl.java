package io.malevich.server.services.document;

import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.DocumentEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.repositories.document.DocumentDao;
import io.malevich.server.services.participant.ParticipantService;
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
    private ParticipantService participantService;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentEntity> findDocs() {
        ParticipantEntity current = participantService.getCurrent();
        return this.documentDao.findByParticipant_Id(current.getId());
    }

    @Override
    @Transactional
    public DocumentEntity save(DocumentEntity entity) {
        ParticipantEntity current = participantService.getCurrent();
        entity.setParticipant(current);
        entity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        return this.documentDao.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ParticipantEntity current = participantService.getCurrent();
        DocumentEntity existing = documentDao.findById(id).orElse(null);

        if(existing == null || existing.getParticipant().getId() != current.getId())
            return;

        this.documentDao.deleteById(id);
    }
}
