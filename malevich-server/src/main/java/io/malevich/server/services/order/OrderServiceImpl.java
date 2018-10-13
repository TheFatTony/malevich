package io.malevich.server.services.order;

import io.malevich.server.dao.order.OrderDao;
import io.malevich.server.entity.OrderEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;


  @Override
  @Transactional(readOnly = true)
  public List<OrderEntity> findAll() {
        return this.orderDao.findAll();
  }

}
