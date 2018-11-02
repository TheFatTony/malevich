package io.malevich.server.services.ordertype;

import io.malevich.server.domain.OrderTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface OrderTypeService {

    List<OrderTypeEntity> findAll();

    Optional<OrderTypeEntity> findById(String id);

}
