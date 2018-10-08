package io.malevich.server.services.trader;


import io.malevich.server.dao.trader.TraderDao;
import io.malevich.server.entity.TraderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private TraderDao traderDao;

    protected TraderServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraderEntity> findAll() {
        return traderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity find(Long id) { return traderDao.findById(id).get(); }

    @Override
    public TraderEntity findByUserName(String name) { return traderDao.findByUserName(name).get(); }

    @Override
    @Transactional
    public TraderEntity update(TraderEntity trader) {
        return traderDao.save(trader);
    }
}
