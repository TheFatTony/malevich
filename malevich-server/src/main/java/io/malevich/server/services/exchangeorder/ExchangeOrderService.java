package io.malevich.server.services.exchangeorder;

import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import org.knowm.xchange.dto.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface ExchangeOrderService {

    List<ExchangeOrderEntity> findAll();

    ExchangeOrderEntity findByOrderId(String orderId);

    void save(Order order, PaymentMethodEntity paymentMethodEntity, String exchangeName, String orderId);

    ExchangeOrderEntity save(ExchangeOrderEntity entity);

}
