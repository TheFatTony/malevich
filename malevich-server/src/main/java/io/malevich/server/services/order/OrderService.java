package io.malevich.server.services.order;

import io.malevich.server.domain.OrderEntity;
import io.malevich.server.exceptions.AccountStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Service
public interface OrderService {


    List<OrderEntity> getPlacedOrders();

    List<OrderEntity> getOrdersByArtworkStockId(Long artworkId);

    List<OrderEntity> getOpenOrdersByArtworkStockId(Long artworkId);

    void placeAsk(OrderEntity orderEntity);

    void placeBid(OrderEntity orderEntity);

    void cancelOrder(OrderEntity orderEntity);

    void cancelOwnOrder(OrderEntity orderEntity);
}
