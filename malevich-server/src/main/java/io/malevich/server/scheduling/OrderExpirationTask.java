package io.malevich.server.scheduling;

import io.malevich.server.domain.OrderEntity;
import io.malevich.server.exceptions.AccountStateException;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.services.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Profile("test")
@Component
public class OrderExpirationTask {

    @Autowired
    private OrderService orderService;


    @Scheduled(cron = "0 0 00 * * *")
    public void reportCurrentTime() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        orderService.findAllOpen()
                .stream()
                .filter(order -> order.getExpirationDate() != null && order.getExpirationDate().compareTo(now) <= 0)
                .forEach(order -> orderService.cancelOrder(order)
        );
    }
}
