package io.malevich.server.services.delayedchange;

import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.domain.TraderEntity;
import io.malevich.server.repositories.delayedchange.DelayedChangeDao;
import io.malevich.server.services.trader.TraderService;
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
    private TraderService traderService;


    @Override
    @Transactional(readOnly = true)
    public DelayedChangeEntity save(DelayedChangeEntity delayedChange) {
        return delayedChangeDao.save(delayedChange);
    }

    @Override
    @Transactional
    public void approveChange(DelayedChangeEntity delayedChangeEntity) {
        if (delayedChangeEntity.getTypeId().equals("TRADER")) {
            traderService.save((TraderEntity) delayedChangeEntity.getPayload());
        }
    }

    @Override
    public List<DelayedChangeEntity> findAll() {
        return delayedChangeDao.findAll();
    }

}
