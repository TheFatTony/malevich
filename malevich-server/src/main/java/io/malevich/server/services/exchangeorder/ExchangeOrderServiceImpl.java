package io.malevich.server.services.exchangeorder;

import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.repositories.exchangeorder.ExchangeOrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ExchangeOrderServiceImpl implements ExchangeOrderService {

    @Autowired
    private ExchangeOrderDao dao;


    @Override
    @Transactional(readOnly = true)
    public List<ExchangeOrderEntity> findAll() {
        return this.dao.findAll();
    }

    @Override
    public ExchangeOrderEntity save(ExchangeOrderEntity entity) {
        return this.dao.save(entity);
    }

}
