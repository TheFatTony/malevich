package io.malevich.server.services.documenttype;

import io.malevich.server.domain.DocumentTypeEntity;
import io.malevich.server.repositories.documenttype.DocumentTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeDao documentTypeDao;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeEntity> findByParticipantTypeId(String participantTypeId) {
        return this.documentTypeDao.findByParticipantTypes_Id(participantTypeId);
    }
}
