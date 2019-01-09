package io.malevich.server.services.tradetype;

import io.malevich.server.domain.TradeTypeEntity;
import io.malevich.server.repositories.tradetype.TradeTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TradeTypeServiceImpl implements TradeTypeService {

    private final Map<String, TradeTypeEntity> values;

    private TradeTypeDao tradeTypeDao;

    @Autowired
    public TradeTypeServiceImpl(TradeTypeDao tradeTypeDao) {
        this.tradeTypeDao = tradeTypeDao;
        values = tradeTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradeTypeEntity> findAll() {
        return this.tradeTypeDao.findAll();
    }

    @Override
    public TradeTypeEntity getGtc() {
        return values.get("GTC_");
    }
}
