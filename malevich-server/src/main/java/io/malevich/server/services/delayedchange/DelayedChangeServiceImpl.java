package io.malevich.server.services.delayedchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.domain.TraderEntity;
import io.malevich.server.repositories.delayedchange.DelayedChangeDao;
import io.malevich.server.services.trader.TraderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public DelayedChangeEntity save(DelayedChangeEntity delayedChange) {
        return delayedChangeDao.save(delayedChange);
    }

    @Override
    @Transactional
    public void approveChange(DelayedChangeEntity delayedChangeEntity) {
        if (delayedChangeEntity.getTypeId().equals("TRADER")) {
            TraderEntity traderEntity = new TraderEntity();
            traderEntity = modelMapper.map(delayedChangeEntity.getPayload(), TraderEntity.class);
            traderService.save(traderEntity);
            delayedChangeDao.delete(delayedChangeEntity);
        }
    }

    @Override
    public List<DelayedChangeEntity> findAll() {
        return delayedChangeDao.findAll();
    }

}
