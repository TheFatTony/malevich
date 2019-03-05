package io.malevich.server.services.order;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.fabric.services.cancelorder.CancelOrderTransactionService;
import io.malevich.server.fabric.services.order.OrderTransactionService;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.tradetype.TradeTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private TradeTypeService tradeTypeService;

    @Autowired
    private OrderTypeService orderTypeService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderTransactionService orderTransactionService;

    @Autowired
    private CancelOrderTransactionService cancelOrderTransactionService;

    @Autowired
    private ArtworkStockService artworkStockService;

    @Autowired
    private ParticipantService participantService;

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getPlacedOrders() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        List<OrderTransaction> fabricOrders = orderTransactionService.getOpenOrdersByCounterparty();

        List<OrderEntity> result = new ArrayList<>();

        for (OrderTransaction order : fabricOrders) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(orderStatusService.getValues().get(order.getOrder().getOrderStatus()));
            orderEntity.setType(orderTypeService.getValues().get(order.getOrder().getOrderType()));
            orderEntity.setAmount(order.getOrder().getAmount());

            orderEntity.setIsOwn(participantEntity.getId().toString().equals(order.getOrder().getCounterparty().replace("resource:io.malevich.network.Trader#", "").replace("resource:io.malevich.network.Gallery#", "")));

            orderEntity.setId(order.getOrder().getId());
            orderEntity.setArtworkStock(artworkStockService.find(new Long(order.getOrder().getArtworkStock().replace("resource:io.malevich.network.ArtworkStock#", ""))));
            orderEntity.setTradeType(tradeTypeService.getGtc());
            result.add(orderEntity);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByArtworkStockId(Long artworkId) {
        ParticipantEntity participantEntity = participantService.getCurrent();

        ArtworkStockEntity artworkStockEntity = artworkStockService.find(artworkId);

        List<OrderTransaction> fabricOrders = orderTransactionService.getOrdersByArtworkStock(artworkId);

        List<OrderEntity> result = new ArrayList<>();

        for (OrderTransaction order : fabricOrders) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(orderStatusService.getValues().get(order.getOrder().getOrderStatus()));
            orderEntity.setType(orderTypeService.getValues().get(order.getOrder().getOrderType()));
            orderEntity.setAmount(order.getOrder().getAmount());

            orderEntity.setIsOwn(participantEntity.getId().toString().equals(order.getOrder().getCounterparty().replace("resource:io.malevich.network.Trader#", "").replace("resource:io.malevich.network.Gallery#", "")));

            orderEntity.setArtworkStock(artworkStockEntity);
            orderEntity.setTradeType(tradeTypeService.getGtc());
            orderEntity.setId(order.getOrder().getId());
            orderEntity.setEffectiveDate(order.getTimestamp());
            result.add(orderEntity);
        }

        return result;

    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOpenOrdersByArtworkStockId(Long artworkId) {
        ParticipantEntity participantEntity = participantService.getCurrent();

        ArtworkStockEntity artworkStockEntity = artworkStockService.find(artworkId);

        List<OrderTransaction> fabricOrders = orderTransactionService.getOpenOrdersByArtworkStock(artworkId);

        List<OrderEntity> result = new ArrayList<>();

        for (OrderTransaction order : fabricOrders) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(orderStatusService.getValues().get(order.getOrder().getOrderStatus()));
            orderEntity.setType(orderTypeService.getValues().get(order.getOrder().getOrderType()));
            orderEntity.setAmount(order.getOrder().getAmount());

            orderEntity.setIsOwn(participantEntity.getId().toString().equals(order.getOrder().getCounterparty().replace("resource:io.malevich.network.Trader#", "").replace("resource:io.malevich.network.Gallery#", "")));

            orderEntity.setArtworkStock(artworkStockEntity);
            orderEntity.setTradeType(tradeTypeService.getGtc());
            orderEntity.setId(order.getOrder().getId());
            orderEntity.setEffectiveDate(order.getTimestamp());
            result.add(orderEntity);
        }

        return result;

    }

    private Timestamp setEndOfDay(Timestamp date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);

        date.setTime(c.getTimeInMillis());

        return date;
    }

    @Override
    @Transactional
    public void placeAsk(OrderEntity orderEntity) {


        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParticipant(participantService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.getGtc());
        orderEntity.setType(orderTypeService.getAsk());
        orderEntity.setStatus(orderStatusService.getOpen());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        orderTransactionService.create(orderEntity);

    }


    @Override
    @Transactional()
    public void placeBid(OrderEntity orderEntity) {
        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParticipant(participantService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.getGtc());
        orderEntity.setType(orderTypeService.getBid());
        orderEntity.setStatus(orderStatusService.getOpen());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        orderTransactionService.create(orderEntity);

    }

    @Override
    public void cancelOrder(OrderEntity orderEntity) {
        orderEntity.setStatus(orderStatusService.getCanceled());
        orderEntity.setParticipant(participantService.getCurrent());
        cancelOrderTransactionService.create(orderEntity);
    }

    @Override
    public void cancelOwnOrder(OrderEntity orderEntity) {
        orderEntity.setStatus(orderStatusService.getCanceled());
        orderEntity.setParticipant(participantService.getCurrent());
        cancelOrderTransactionService.create(orderEntity);
    }

}
