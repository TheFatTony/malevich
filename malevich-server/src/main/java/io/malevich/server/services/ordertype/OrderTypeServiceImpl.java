package io.malevich.server.services.ordertype;

import io.malevich.server.domain.OrderTypeEntity;
import io.malevich.server.repositories.ordertype.OrderTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderTypeServiceImpl implements OrderTypeService {


    private final Map<String, OrderTypeEntity> values;

    private OrderTypeDao orderTypeDao;

    @Autowired
    public OrderTypeServiceImpl(OrderTypeDao orderTypeDao) {
        this.orderTypeDao = orderTypeDao;
        values = orderTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderTypeEntity> findAll() {
        return this.orderTypeDao.findAll();
    }

    @Override
    public OrderTypeEntity getAsk() {
        return getValues().get("ASK");
    }

    @Override
    public OrderTypeEntity getBid() {
        return getValues().get("BID");
    }

    @Override
    public Map<String, OrderTypeEntity> getValues() {
        return values;
    }

}
