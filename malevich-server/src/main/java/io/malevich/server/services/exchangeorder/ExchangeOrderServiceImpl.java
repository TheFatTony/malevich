package io.malevich.server.services.exchangeorder;

import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.repositories.exchangeorder.ExchangeOrderDao;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Slf4j
@Service
public class ExchangeOrderServiceImpl implements ExchangeOrderService {

    @Autowired
    private ExchangeOrderDao dao;


    @Override
    @Transactional(readOnly = true)
    public List<ExchangeOrderEntity> findAll() {
        return this.dao.findAll();
    }


    @Override
    public void save(Order order, PaymentMethodEntity paymentMethodEntity, String exchangeName, String orderId) {
        ExchangeOrderEntity entity = new ExchangeOrderEntity();
        entity.setPaymentMethod(paymentMethodEntity);
        entity.setExchangeName(exchangeName);
        entity.setInternalStatus(ExchangeOrderStatus.SUBMITTED);
        entity.setOriginalAmount(order.getOriginalAmount());
        entity.setCurrencyPair(order.getCurrencyPair().toString());
        entity.setOrderId(orderId);
        entity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));

        save(entity);
    }

    @Override
    public ExchangeOrderEntity save(ExchangeOrderEntity entity) {
        return this.dao.save(entity);
    }

}
