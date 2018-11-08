package io.malevich.server.services.order;

import io.malevich.server.domain.*;
import io.malevich.server.repositories.order.OrderDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.services.transaction.TransactionService;
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
    public void placeAsk(OrderEntity orderEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;

            GalleryEntity galleryEntity = galleryService.findByUserName(userDetails.getUsername());

            orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
            orderEntity.setParty(counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId()));
            orderEntity.setTradeType(tradeTypeService.findById("GTC_").get());
            orderEntity.setType(orderTypeService.findById("ASK").get());
            orderEntity.setStatus(orderStatusService.findById("OPEN").get());

            if(orderEntity.getExpirationDate() != null)
                setEndOfDay(orderEntity.getExpirationDate());

            orderEntity = orderDao.save(orderEntity);

            transactionService.placeAsk(orderEntity);

            // TODO fix this crap
        } else
            System.out.println("!!!!!!!!!!!!");
    }

    private Timestamp setEndOfDay(Timestamp date){
        Calendar c=Calendar.getInstance();
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
    public void placeBid(OrderEntity orderEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;

            TraderEntity traderEntity = traderService.findByUserName(userDetails.getUsername());

            orderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
            orderEntity.setParty(counterpartyService.findCounterpartyEntitiesByTraderId(traderEntity.getId()));
            orderEntity.setTradeType(tradeTypeService.findById("GTC_").get());
            orderEntity.setType(orderTypeService.findById("BID").get());
            orderEntity.setStatus(orderStatusService.findById("OPEN").get());

            if(orderEntity.getExpirationDate() != null)
                setEndOfDay(orderEntity.getExpirationDate());

            orderEntity = orderDao.save(orderEntity);

            transactionService.placeBid(orderEntity);

            OrderEntity counterOrderEntity = orderDao.findCounterOrder(orderEntity.getArtworkStock().getId(), orderEntity.getAmount());
            if (counterOrderEntity != null) {
                TradeHistoryEntity tradeHistoryEntity = tradeHistoryService.create(counterOrderEntity, orderEntity);
                orderEntity.setStatus(orderStatusService.findById("EXECUTED").get());
                orderDao.save(orderEntity);
                counterOrderEntity.setStatus(orderStatusService.findById("EXECUTED").get());
                orderDao.save(counterOrderEntity);

                transactionService.buySell(tradeHistoryEntity);

                List<OrderEntity> orders = orderDao.findAllOrdersByArtworkStockId(tradeHistoryEntity.getArtworkStock().getId());
                for (OrderEntity order : orders) {
                    if ("BID".equals(order.getType().getId())) {
                        if (!"EXECUTED".equals(order.getStatus().getId())) {
                            transactionService.cancelBid(order);
                            order.setStatus(orderStatusService.findById("CANCELED").get());
                            orderDao.save(order);
                        }
                    }
                }
            }

            // TODO fix this crap
        } else
            System.out.println("!!!!!!!!!!!!");
    }

    @Override
    @Transactional
    public void cancelOrder(OrderEntity orderEntity)
    {
        if("CANCELED".equals(orderEntity.getStatus().getId()))
            return;

        orderEntity.setStatus(orderStatusService.findById("CANCELED").get());
        orderDao.save(orderEntity);

        switch(orderEntity.getType().getId()){
            case "ASK":{
                transactionService.cancelAsk(orderEntity);
                break;
            }
            case "BID":{
                transactionService.cancelBid(orderEntity);
                break;
            }
        }
    }


}
