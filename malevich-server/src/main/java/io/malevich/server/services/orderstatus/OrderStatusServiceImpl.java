package io.malevich.server.services.orderstatus;

import io.malevich.server.domain.OrderStatusEntity;
import io.malevich.server.repositories.orderstatus.OrderStatusDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class OrderStatusServiceImpl implements OrderStatusService {


    private final OrderStatusEntity open;
    private final OrderStatusEntity executed;
    private final OrderStatusEntity canceled;
    private OrderStatusDao dao;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusDao dao) {
        this.dao = dao;
        open = dao.findById("OPEN").get();
        executed = dao.findById("EXECUTED").get();
        canceled = dao.findById("CANCELED").get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusEntity> findAll() {
        return this.dao.findAll();
    }

    @Override
    public OrderStatusEntity getOpen() {
        return open;
    }

    @Override
    public OrderStatusEntity getExecuted() {
        return executed;
    }

    @Override
    public OrderStatusEntity getCanceled() {
        return canceled;
    }
}
