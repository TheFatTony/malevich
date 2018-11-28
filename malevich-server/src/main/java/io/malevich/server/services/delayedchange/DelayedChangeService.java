package io.malevich.server.services.delayedchange;

import io.malevich.server.domain.DelayedChangeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DelayedChangeService {


    DelayedChangeEntity save(DelayedChangeEntity delayedChange);

    void approveChange(DelayedChangeEntity delayedChangeEntity);

    List<DelayedChangeEntity> findAll();
}
