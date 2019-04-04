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
import java.util.Optional;


@Slf4j
@Service
public class ExchangeOrderServiceImpl implements ExchangeOrderService {

    @Autowired
    private ExchangeOrderDao exchangeOrderDao;


    @Override
    @Transactional(readOnly = true)
    public List<ExchangeOrderEntity> findAll() {
        return this.exchangeOrderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ExchangeOrderEntity findByOrderId(String orderId) {
        Optional<ExchangeOrderEntity> exchangeOrderEntity = exchangeOrderDao.findByOrderId(orderId);
        if (exchangeOrderEntity.isPresent())
            return exchangeOrderEntity.get();
        return null;
    }


    @Override
    @Transactional
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
    @Transactional
    public ExchangeOrderEntity save(ExchangeOrderEntity entity) {
        return this.exchangeOrderDao.save(entity);
    }

}
