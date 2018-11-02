package io.malevich.server.services.order;

import io.malevich.server.domain.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {

    List<OrderEntity> findAll();

    List<OrderEntity> getPlacedOrders();

    List<OrderEntity> getOrdersByArtworkId(Long artworkId);

    void placeAsk(OrderEntity orderEntity);

    void placeBid(OrderEntity orderEntity);
}
