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

    @Autowired
    private OrderStatusDao dao;


    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusEntity> findAll() {
        return this.dao.findAll();
    }

    @Override
    public Optional<OrderStatusEntity> findById(String id) {
        return dao.findById(id);
    }

}
