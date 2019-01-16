package io.malevich.server.services.order;

import io.malevich.server.domain.*;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.fabric.services.order.OrderTransactionService;
import io.malevich.server.repositories.order.OrderDao;
import io.malevich.server.repositories.transactiongroup.TransactionGroupDao;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TransactionGroupDao transactionGroupDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TradeTypeService tradeTypeService;

    @Autowired
    private OrderTypeService orderTypeService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private OrderTransactionService orderTransactionService;

    @Autowired
    private ArtworkStockService artworkStockService;

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findAll() {
        return this.orderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findAllOpen() {
        return this.orderDao.findAllOpen();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findOldOpen(Timestamp date) {
        return this.orderDao.findOldOpen(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getPlacedOrders() {
        List<OrderTransaction> fabricOrders = orderTransactionService.getOrdersByCounterparty();

        List<OrderEntity> result = new ArrayList<>();

        for (OrderTransaction order: fabricOrders) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(orderStatusService.getValues().get(order.getOrder().getOrderStatus()));
            orderEntity.setType(orderTypeService.getValues().get(order.getOrder().getOrderType()));
            orderEntity.setAmount(order.getOrder().getAmount());
            orderEntity.setIsOwn(false);
            orderEntity.setArtworkStock(artworkStockService.find(new Long(order.getOrder().getArtworkStock().replace("resource:io.malevich.network.ArtworkStock#", ""))));
            orderEntity.setTradeType(tradeTypeService.getGtc());
            result.add(orderEntity);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByArtworkStockId(Long artworkId) {

        ArtworkStockEntity artworkStockEntity = artworkStockService.find(artworkId);

        List<OrderTransaction> fabricOrders = orderTransactionService.getOrdersByArtworkStock(artworkId);

        List<OrderEntity> result = new ArrayList<>();

        for (OrderTransaction order: fabricOrders) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(orderStatusService.getValues().get(order.getOrder().getOrderStatus()));
            orderEntity.setType(orderTypeService.getValues().get(order.getOrder().getOrderType()));
            orderEntity.setAmount(order.getOrder().getAmount());
            orderEntity.setIsOwn(false);
            orderEntity.setArtworkStock(artworkStockEntity);
            orderEntity.setTradeType(tradeTypeService.getGtc());
            result.add(orderEntity);
        }

        return result;

    }

    @Override
    @Transactional
    public void placeAsk(OrderEntity orderEntity) {
        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParty(counterpartyService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.getGtc());
        orderEntity.setType(orderTypeService.getAsk());
        orderEntity.setStatus(orderStatusService.getOpen());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        List<OrderEntity> orders = orderDao.findAllOrdersByArtworkStockId(orderEntity.getArtworkStock().getId());

        orders = cancelClones(orderEntity, orders);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("ASK");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);
        orderEntity.setTransactionGroup(transactionGroupEntity);

        orderEntity = orderDao.save(orderEntity);

        orderTransactionService.create(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();

        transactionService.createTransactionAndReverse(transactionTypeService.getAsk(), orderEntity.getTransactionGroup(), orderEntity.getParty(), malevich, orderEntity.getArtworkStock(), 0D, -1L);

        tryExecute(orderEntity, orders);
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
    @Transactional()
    public void placeBid(OrderEntity orderEntity) {
        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParty(counterpartyService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.getGtc());
        orderEntity.setType(orderTypeService.getBid());
        orderEntity.setStatus(orderStatusService.getOpen());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        List<OrderEntity> orders = orderDao.findAllOrdersByArtworkStockId(orderEntity.getArtworkStock().getId());

        orders = cancelClones(orderEntity, orders);

        TransactionGroupEntity transactionGroupEntity = new TransactionGroupEntity();
        transactionGroupEntity.setType("BID");
        transactionGroupEntity = transactionGroupDao.save(transactionGroupEntity);
        orderEntity.setTransactionGroup(transactionGroupEntity);

        orderEntity = orderDao.save(orderEntity);

        orderTransactionService.create(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();
        transactionService.createTransactionAndReverse(transactionTypeService.getBid(), orderEntity.getTransactionGroup(), orderEntity.getParty(), malevich, null, -orderEntity.getAmount(), 0L);

        tryExecute(orderEntity, orders);
    }

    private OrderEntity getBestAsk(List<OrderEntity> screen) {
        return screen.stream()
                .filter(o -> orderTypeService.getAsk().getId().equals(o.getType().getId()))
                .sorted((o1, o2) -> {
                    if (o1.getAmount() != o2.getAmount())
                        return o1.getAmount().compareTo(o2.getAmount());

                    return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
                })
                .findFirst()
                .orElse(null);
    }

    private OrderEntity getBestBid(List<OrderEntity> screen) {
        return screen.stream()
                .filter(o -> orderTypeService.getBid().getId().equals(o.getType().getId()))
                .sorted((o1, o2) -> {
                    if (o1.getAmount() != o2.getAmount())
                        return -o1.getAmount().compareTo(o2.getAmount());

                    return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
                })
                .findFirst()
                .orElse(null);
    }

    private TradeHistoryEntity tryExecute(OrderEntity orderEntity, List<OrderEntity> screen) {
        OrderEntity bestBid = getBestBid(screen);
        OrderEntity bestAsk = getBestAsk(screen);

        if (orderTypeService.getBid().getId().equals(orderEntity.getType().getId())) {
            if (bestBid != null && orderEntity.getAmount() <= bestBid.getAmount())
                return null;

            if (bestAsk == null)
                return null;

            bestBid = orderEntity;
        } else {
            if (bestAsk != null && orderEntity.getAmount() >= bestAsk.getAmount())
                return null;

            if (bestBid == null)
                return null;

            bestAsk = orderEntity;
        }

        if (bestBid.getAmount() < bestAsk.getAmount())
            return null;
        else {
            // if amounts are not equal, get price from earliest order
            Double tradePrice = bestAsk.getEffectiveDate().compareTo(bestBid.getEffectiveDate()) < 0
                    ? bestAsk.getAmount()
                    : bestBid.getAmount();

            bestBid.setStatus(orderStatusService.getExecuted());
            bestAsk.setStatus(orderStatusService.getExecuted());

            orderDao.save(bestAsk);
            orderDao.save(bestBid);

            CounterpartyEntity malevich = counterpartyService.getMalevich();

            //return ask
            transactionService.createTransactionAndReverse(transactionTypeService.getReturnAsk(), bestAsk.getTransactionGroup(), bestAsk.getParty(), malevich, orderEntity.getArtworkStock(), 0D, 1L);

            //return bid
            transactionService.createTransactionAndReverse(transactionTypeService.getReturnBid(), bestBid.getTransactionGroup(), bestBid.getParty(), malevich, null, bestBid.getAmount(), 0L);

            //buy-sell
            transactionService.createTransaction(transactionTypeService.getBuySell(), bestAsk.getTransactionGroup(), bestAsk.getParty(), bestBid.getParty(), orderEntity.getArtworkStock(), tradePrice, -1L);
            transactionService.createTransaction(transactionTypeService.getBuySell(), bestBid.getTransactionGroup(), bestBid.getParty(), bestAsk.getParty(), orderEntity.getArtworkStock(), -tradePrice, 1L);

            //todo create blockchain transaction

            return tradeHistoryService.create(bestAsk, bestBid, tradePrice);
        }
    }

    private List<OrderEntity> cancelClones(OrderEntity orderEntity, List<OrderEntity> screen) {
        for (OrderEntity o : screen) {
            if (o.getParty().getId() == orderEntity.getParty().getId())
                cancelOrder(o);
        }

        return screen.stream()
                .filter(o -> !orderStatusService.getCanceled().getId().equals(orderEntity.getStatus().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelOrder(OrderEntity orderEntity) {
        if (orderStatusService.getCanceled().getId().equals(orderEntity.getStatus().getId()))
            return;

        orderEntity.setStatus(orderStatusService.getCanceled());
        orderDao.save(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();

        if (orderTypeService.getAsk().getId().equals(orderEntity.getType().getId())) {
            transactionService.createTransactionAndReverse(transactionTypeService.getCancelAsk(), orderEntity.getTransactionGroup(), orderEntity.getParty(), malevich, orderEntity.getArtworkStock(), 0D, 1L);
        } else if (orderTypeService.getBid().getId().equals(orderEntity.getType().getId())) {
            transactionService.createTransactionAndReverse(transactionTypeService.getCancelBid(), orderEntity.getTransactionGroup(), orderEntity.getParty(), malevich, null, orderEntity.getAmount(), 0L);
        }
    }

    @Override
    @Transactional
    public void cancelOwnOrder(OrderEntity orderEntity) {
        // check if this is my order
        OrderEntity storedEntity = orderDao.findById(orderEntity.getId()).orElse(null);

        if (storedEntity == null)
            // throw error???
            return;

        CounterpartyEntity currentCounterparty = counterpartyService.getCurrent();

        if (!storedEntity.getParty().getId().equals(currentCounterparty.getId()))
            // throw error???
            return;

        cancelOrder(storedEntity);
    }
}
