package io.malevich.server.services.order;

import io.malevich.server.dao.order.OrderDao;
import io.malevich.server.entity.GalleryEntity;
import io.malevich.server.entity.OrderEntity;
import io.malevich.server.entity.TradeHistoryEntity;
import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.services.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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
    public List<OrderEntity> getPlacedOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else return null;

        TraderEntity traderEntity = traderService.findByUserName(userDetails.getUsername());
        if(traderEntity != null)
            return this.orderDao.findAllPlacedTraderOrders(traderEntity.getId());

        GalleryEntity galleryEntity = galleryService.findByUserName(userDetails.getUsername());
        if(galleryEntity != null)
            return this.orderDao.findAllPlacedGalleryOrders(galleryEntity.getId());

        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByArtworkId(Long artworkId) {
        return orderDao.findAllOrdersByArtworkId(artworkId);
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

            orderEntity = orderDao.save(orderEntity);

            transactionService.placeAsk(orderEntity);

            // TODO fix this crap
        } else
            System.out.println("!!!!!!!!!!!!");
    }

    @Override
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

            orderEntity = orderDao.save(orderEntity);

            transactionService.placeBid(orderEntity);

            OrderEntity counterOrderEntity = orderDao.findCounterOrder(orderEntity.getArtworkStock().getId(), orderEntity.getAmount());
            if (counterOrderEntity != null) {
                TradeHistoryEntity tradeHistoryEntity =  tradeHistoryService.create(counterOrderEntity,orderEntity);

                transactionService.buySell(tradeHistoryEntity);
            }

            // TODO fix this crap
        } else
            System.out.println("!!!!!!!!!!!!");
    }


}
