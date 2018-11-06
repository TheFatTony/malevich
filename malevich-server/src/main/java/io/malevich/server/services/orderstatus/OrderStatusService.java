package io.malevich.server.services.orderstatus;

import io.malevich.server.domain.OrderStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface OrderStatusService {

    List<OrderStatusEntity> findAll();

    Optional<OrderStatusEntity> findById(String id);

}
