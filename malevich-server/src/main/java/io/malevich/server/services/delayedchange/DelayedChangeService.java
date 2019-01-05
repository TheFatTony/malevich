package io.malevich.server.services.delayedchange;

import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.domain.Entity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface DelayedChangeService {


    DelayedChangeEntity save(DelayedChangeEntity delayedChange);

    DelayedChangeEntity saveEntity(Entity<Long> entity);

    void approveChange(DelayedChangeEntity delayedChangeEntity);

    void declineChange(DelayedChangeEntity delayedChangeEntity, String comments);

    List<DelayedChangeEntity> findAll();

    DelayedChangeEntity findByTypeIdAndAndReferenceId(String typeId, Long referenceId);
}
