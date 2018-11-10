package io.malevich.server.services.order;

import io.malevich.server.domain.*;
import io.malevich.server.exceptions.AccountStateException;
import io.malevich.server.repositories.order.OrderDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TraderService traderService;

    @Autowired
    private GalleryService galleryService;

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

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findAll() {
        return this.orderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> findAllOpen() {
        // todo add date param
        return this.orderDao.findAllOpen();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getPlacedOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else return null;

        TraderEntity traderEntity = traderService.findByUserName(userDetails.getUsername());
        if (traderEntity != null)
            return this.orderDao.findAllPlacedTraderOrders(traderEntity.getId());

        GalleryEntity galleryEntity = galleryService.findByUserName(userDetails.getUsername());
        if (galleryEntity != null)
            return this.orderDao.findAllPlacedGalleryOrders(galleryEntity.getId());

        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByArtworkStockId(Long artworkId) {
        return orderDao.findAllOrdersByArtworkStockId(artworkId);
    }

    @Override
    @Transactional
    public void placeAsk(OrderEntity orderEntity) throws AccountStateException {
        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParty(counterpartyService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.findById("GTC_").get());
        orderEntity.setType(orderTypeService.findById("ASK").get());
        orderEntity.setStatus(orderStatusService.findById("OPEN").get());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        List<OrderEntity> orders = orderDao.findAllOrdersByArtworkStockId(orderEntity.getArtworkStock().getId());

        orders = cancelClones(orderEntity, orders);

        orderEntity = orderDao.save(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();

        transactionService.createTransactionAndReverse(transactionTypeService.findById("0004").get(), orderEntity.getParty(), malevich, orderEntity.getArtworkStock(), 0D, -1L);

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
    @Transactional
    public void placeBid(OrderEntity orderEntity) throws AccountStateException {
        orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setParty(counterpartyService.getCurrent());
        if (orderEntity.getTradeType() == null)
            orderEntity.setTradeType(tradeTypeService.findById("GTC_").get());
        orderEntity.setType(orderTypeService.findById("BID").get());
        orderEntity.setStatus(orderStatusService.findById("OPEN").get());
        if (orderEntity.getExpirationDate() != null)
            setEndOfDay(orderEntity.getExpirationDate());

        List<OrderEntity> orders = orderDao.findAllOrdersByArtworkStockId(orderEntity.getArtworkStock().getId());

        orders = cancelClones(orderEntity, orders);

        orderEntity = orderDao.save(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();
        transactionService.createTransactionAndReverse(transactionTypeService.findById("0003").get(), orderEntity.getParty(), malevich, null, -orderEntity.getAmount(), 0L);

        tryExecute(orderEntity, orders);
    }

    private OrderEntity getBestAsk(List<OrderEntity> screen) {
        return screen.stream()
                .filter(o -> "ASK".equals(o.getType().getId()))
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
                .filter(o -> "BID".equals(o.getType().getId()))
                .sorted((o1, o2) -> {
                    if (o1.getAmount() != o2.getAmount())
                        return -o1.getAmount().compareTo(o2.getAmount());

                    return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
                })
                .findFirst()
                .orElse(null);
    }

    private TradeHistoryEntity tryExecute(OrderEntity orderEntity, List<OrderEntity> screen) throws AccountStateException {
        OrderEntity bestBid = getBestBid(screen);
        OrderEntity bestAsk = getBestAsk(screen);

        if ("BID".equals(orderEntity.getType().getId())) {
            if (bestBid != null && orderEntity.getAmount() <= bestBid.getAmount())
                return null;

            if(bestAsk == null)
                return null;

            bestBid = orderEntity;
        } else {
            if (bestAsk != null && orderEntity.getAmount() >= bestAsk.getAmount())
                return null;

            if(bestBid == null)
                return null;

            bestAsk = orderEntity;
        }

        if (bestBid.getAmount() < bestAsk.getAmount())
            return null;
        else {
            Double tradePrice = bestAsk.getEffectiveDate().compareTo(bestAsk.getEffectiveDate()) < 0
                    ? bestAsk.getAmount()
                    : bestBid.getAmount();

            bestBid.setStatus(orderStatusService.findById("EXECUTED").get());
            bestAsk.setStatus(orderStatusService.findById("EXECUTED").get());

            orderDao.save(bestAsk);
            orderDao.save(bestBid);

            CounterpartyEntity malevich = counterpartyService.getMalevich();

            //return ask
            transactionService.createTransactionAndReverse(transactionTypeService.findById("0009").get(), bestAsk.getParty(), malevich, orderEntity.getArtworkStock(), 0D, 1L);

            //return bid
            transactionService.createTransactionAndReverse(transactionTypeService.findById("0008").get(), bestBid.getParty(), malevich, null, bestBid.getAmount(), 0L);

            //buy-sell
            transactionService.createTransactionAndReverse(transactionTypeService.findById("0005").get(), bestAsk.getParty(), bestBid.getParty(), orderEntity.getArtworkStock(), tradePrice, -1L);

            //todo create blockchain transaction

            return tradeHistoryService.create(bestAsk, bestBid);
        }
    }

    private List<OrderEntity> cancelClones(OrderEntity orderEntity, List<OrderEntity> screen) throws AccountStateException {
        for (OrderEntity o : screen) {
            if (o.getParty().getId() == orderEntity.getParty().getId())
                cancelOrder(o);
        }

        return screen.stream()
                .filter(o -> !"CANCELED".equals(orderEntity.getStatus().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelOrder(OrderEntity orderEntity) throws AccountStateException {
        if ("CANCELED".equals(orderEntity.getStatus().getId()))
            return;

        orderEntity.setStatus(orderStatusService.findById("CANCELED").get());
        orderDao.save(orderEntity);

        CounterpartyEntity malevich = counterpartyService.getMalevich();

        switch (orderEntity.getType().getId()) {
            case "ASK": {
                transactionService.createTransactionAndReverse(transactionTypeService.findById("0007").get(), orderEntity.getParty(), malevich, orderEntity.getArtworkStock(), 0D, 1L);
                break;
            }
            case "BID": {
                transactionService.createTransactionAndReverse(transactionTypeService.findById("0006").get(), orderEntity.getParty(), malevich, null, orderEntity.getAmount(), 0L);

                break;
            }
        }
    }


}
