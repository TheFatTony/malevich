package io.malevich.server.services.order;

import io.malevich.server.entity.ArtworkStockEntity;
import io.malevich.server.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {

    List<OrderEntity> findAll();

    List<OrderEntity> getPlacedOrders();

    void placeBid(OrderEntity orderEntity);
}
