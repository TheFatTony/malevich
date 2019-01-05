package io.malevich.server.services.delayedchange;

import io.malevich.server.domain.*;
import io.malevich.server.repositories.delayedchange.DelayedChangeDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.document.DocumentService;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Slf4j
@Service
public class DelayedChangeServiceImpl implements DelayedChangeService {

    @Autowired
    private DelayedChangeDao delayedChangeDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailQueueService mailQueueService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public DelayedChangeEntity save(DelayedChangeEntity delayedChange) {
        return delayedChangeDao.save(delayedChange);
    }

    @Override
    @Transactional
    public DelayedChangeEntity saveEntity(Entity<Long> entity) {
        UserEntity currentUser = userService.getCurrent();

        DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
        delayedChangeEntity.setPayload(entity);
        delayedChangeEntity.setReferenceId(entity.getId());
        delayedChangeEntity.setUser(currentUser);

        if (entity instanceof CounterpartyEntity)
            delayedChangeEntity.setTypeId("COUNTERPARTY");
        else if (entity instanceof DocumentEntity)
            delayedChangeEntity.setTypeId("DOCUMENT");
        else
            return null;

        return save(delayedChangeEntity);
    }

    @Override
    @Transactional
    public void approveChange(DelayedChangeEntity delayedChangeEntity) {
        if (delayedChangeEntity.getTypeId().equals("COUNTERPARTY")) {
            CounterpartyEntity counterpartyEntity =
                    modelMapper.map(delayedChangeEntity.getPayload(), CounterpartyEntity.class);
            counterpartyService.save(counterpartyEntity);
            delayedChangeDao.delete(delayedChangeEntity);
        } else if (delayedChangeEntity.getTypeId().equals("DOCUMENT")) {
            DocumentEntity document =
                    modelMapper.map(delayedChangeEntity.getPayload(), DocumentEntity.class);
            documentService.save(document);
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
