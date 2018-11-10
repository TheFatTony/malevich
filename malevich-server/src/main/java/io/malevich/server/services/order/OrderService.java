package io.malevich.server.services.order;

import io.malevich.server.domain.OrderEntity;
import io.malevich.server.exceptions.AccountStateException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {

    List<OrderEntity> findAll();

    List<OrderEntity> findAllOpen();

    List<OrderEntity> getPlacedOrders();

    List<OrderEntity> getOrdersByArtworkStockId(Long artworkId);

    void placeAsk(OrderEntity orderEntity) throws AccountStateException;

    void placeBid(OrderEntity orderEntity) throws AccountStateException;

    void cancelOrder(OrderEntity orderEntity) throws AccountStateException;
}
