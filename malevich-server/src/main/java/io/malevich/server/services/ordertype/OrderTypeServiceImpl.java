package io.malevich.server.services.ordertype;

import io.malevich.server.repositories.ordertype.OrderTypeDao;
import io.malevich.server.domain.OrderTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class OrderTypeServiceImpl implements OrderTypeService {


    private final OrderTypeEntity ask;
    private final OrderTypeEntity bid;

    private OrderTypeDao orderTypeDao;

    @Autowired
    public OrderTypeServiceImpl(OrderTypeDao orderTypeDao){
        this.orderTypeDao = orderTypeDao;
        ask = orderTypeDao.findById("ASK").get();
        bid = orderTypeDao.findById("BID").get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderTypeEntity> findAll() {
        return this.orderTypeDao.findAll();
    }

    @Override
    public OrderTypeEntity getAsk() {
        return ask;
    }

    @Override
    public OrderTypeEntity getBid() {
        return bid;
    }

}
