package io.malevich.server.services.order;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrdersDao dao;


  @Override
  @Transactional(readOnly = true)
  public List<OrdersEntity> findAll() {
        return this.dao.findAll();
  }

}
