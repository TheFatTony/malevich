package io.malevich.server.services.orderstatus;

import io.malevich.server.domain.OrderStatusEntity;
import io.malevich.server.domain.PaymentTypeEntity;
import io.malevich.server.repositories.orderstatus.OrderStatusDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderStatusServiceImpl implements OrderStatusService {


    private final Map<String, OrderStatusEntity> values;

    private OrderStatusDao orderStatusDao;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
        values = orderStatusDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusEntity> findAll() {
        return this.orderStatusDao.findAll();
    }

    @Override
    public OrderStatusEntity getOpen() {
        return getValues().get("OPEN");
    }

    @Override
    public OrderStatusEntity getExecuted() {
        return getValues().get("EXECUTED");
    }

    @Override
    public OrderStatusEntity getCanceled() {
        return getValues().get("CANCELED");
    }

    @Override
    public Map<String, OrderStatusEntity> getValues() {
        return values;
    }
}
