package io.malevich.server.services.delayedchange;

import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.domain.MailQueueEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.repositories.delayedchange.DelayedChangeDao;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class DelayedChangeServiceImpl implements DelayedChangeService {


    @Autowired
    private DelayedChangeDao delayedChangeDao;

    @Autowired
    private MailQueueService mailQueueService;

    @Autowired
    private ParticipantService participantService;

    @Override
    @Transactional(readOnly = true)
    public DelayedChangeEntity save(DelayedChangeEntity delayedChange) {
        return delayedChangeDao.save(delayedChange);
    }

    @Override
    @Transactional
    public void approveChange(DelayedChangeEntity delayedChangeEntity) {
        delayedChangeEntity = delayedChangeDao.findById(delayedChangeEntity.getId()).orElse(null);

        if (delayedChangeEntity.getTypeId().equals("PARTICIPANT")) {
            ParticipantEntity participantEntity =
                    participantService.convertToEntity(delayedChangeEntity.getPayload());
            participantService.save(participantEntity, delayedChangeEntity.getUser());
            delayedChangeDao.delete(delayedChangeEntity);
        }
    }

    @Override
    @Transactional
    public void declineChange(DelayedChangeEntity delayedChangeEntity, String comments) {
        delayedChangeEntity = delayedChangeDao.findById(delayedChangeEntity.getId()).get();
        mailQueueService.create(new MailQueueEntity(delayedChangeEntity.getUser().getName(), "Changes declined", comments));
        delayedChangeDao.delete(delayedChangeEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DelayedChangeEntity> findAll() {
        return delayedChangeDao.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public DelayedChangeEntity findByTypeIdAndAndReferenceId(String typeId, Long referenceId) {
        return delayedChangeDao.findFirstByTypeIdAndAndReferenceId(typeId, referenceId).orElse(null);
    }


}
