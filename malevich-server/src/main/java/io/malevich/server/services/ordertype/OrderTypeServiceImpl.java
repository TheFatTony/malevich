package io.malevich.server.services.ordertype;

import io.malevich.server.dao.ordertype.OrderTypeDao;
import io.malevich.server.entity.OrderTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class OrderTypeServiceImpl implements OrderTypeService {

  @Autowired
  private OrderTypeDao orderTypeDao;


  @Override
  @Transactional(readOnly = true)
  public List<OrderTypeEntity> findAll() {
        return this.orderTypeDao.findAll();
  }

  @Override
  public Optional<OrderTypeEntity> findById(String id) {
    return orderTypeDao.findById(id);
  }

}
