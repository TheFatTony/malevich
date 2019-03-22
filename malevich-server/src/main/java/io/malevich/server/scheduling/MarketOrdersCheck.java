package io.malevich.server.scheduling;

import io.malevich.server.services.exchange.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MarketOrdersCheck {


    @Autowired
    private ExchangeService exchangeService;

    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkExecution() {

        try {
            exchangeService.placeOrders();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            exchangeService.processExecution();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
