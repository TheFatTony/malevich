package io.malevich.server.services.ordertype;

import io.malevich.server.entity.OrderTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface OrderTypeService {

  List<OrderTypeEntity> findAll();

}
