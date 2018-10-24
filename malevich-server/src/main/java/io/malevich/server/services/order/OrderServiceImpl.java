package io.malevich.server.services.order;

import io.malevich.server.dao.order.OrderDao;
import io.malevich.server.entity.GalleryEntity;
import io.malevich.server.entity.OrderEntity;
import io.malevich.server.entity.TradeTypeEntity;
import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.services.tradetype.TradeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
        GalleryEntity galleryEntity = galleryService.findByUserName(userDetails.getUsername());
//        TraderEntity traderEntity = traderService.findByUserName(username);
        return this.orderDao.findAllPlacedTraderOrders(galleryEntity.getId());
    }

    @Override
    @Transactional
    public OrderEntity insert(OrderEntity newOrderEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else return null;
        GalleryEntity galleryEntity = galleryService.findByUserName(userDetails.getUsername());

        newOrderEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        newOrderEntity.setParty(counterpartyService.findCounterpartyEntitiesByGalleryId(galleryEntity.getId()));
        newOrderEntity.setTradeType(tradeTypeService.findById("GTC_").get());
        newOrderEntity.setType(orderTypeService.findById("ASK").get());

        return orderDao.save(newOrderEntity);
    }

}
