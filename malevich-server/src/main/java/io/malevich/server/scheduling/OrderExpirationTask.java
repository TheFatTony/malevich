package io.malevich.server.scheduling;

import io.malevich.server.services.order.OrderService;
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

    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        orderService.findOldOpen(now)
                .stream()
                .forEach(order -> orderService.cancelOrder(order)
                );
    }
}
