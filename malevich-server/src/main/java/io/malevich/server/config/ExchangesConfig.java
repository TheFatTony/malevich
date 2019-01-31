package io.malevich.server.config;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesConfig {

    @Bean
    public KrakenExchange krakenExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(KrakenExchange.class);
        spec.setApiKey("xgJbeIwiCvYPX2jeb4cO4AUaKpvOVK6nu4mbFjWNqNnbIXR5L5G8H6km");
        spec.setSecretKey("b+mpApiZdvLh31ONEEQQC9JP8StMxcrEpxVx8yOJordFGBaopGSkxzqUeKXNiOxeSOryWdjUHQiB4X2mWfsPFA==");
        spec.setUserName("AntonTR");

        KrakenExchange krakenExchange = (KrakenExchange) ExchangeFactory.INSTANCE.createExchange(spec);
        return krakenExchange;
    }

}
