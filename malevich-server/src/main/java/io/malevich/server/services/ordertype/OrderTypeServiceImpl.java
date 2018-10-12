package io.malevich.server.services.ordertype;

import io.malevich.server.dao.ordertype.OrderTypeDao;
import io.malevich.server.entity.OrderTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class OrderTypeServiceImpl implements OrderTypeService {

  @Autowired
  private OrderTypeDao dao;


  @Override
  @Transactional(readOnly = true)
  public List<OrderTypeEntity> findAll() {
        return this.dao.findAll();
  }

}
