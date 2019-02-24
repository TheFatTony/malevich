package io.malevich.server.services.delayedchange;

import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.domain.DelayedChangeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DelayedChangeService {


    DelayedChangeEntity save(DelayedChangeEntity delayedChange);

    DelayedChangeEntity saveEntity(YAbstractPersistable<Long> entity);

    void approveChange(DelayedChangeEntity delayedChangeEntity);

    void declineChange(DelayedChangeEntity delayedChangeEntity, String comments);

    List<DelayedChangeEntity> findAll();

    DelayedChangeEntity findByTypeIdAndAndReferenceId(String typeId, String referenceId);
}
