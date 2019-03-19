package io.malevich.server.config;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesConfig {

    @Value("${exchanges.kraken.username}")
    private String userName;

    @Value("${exchanges.kraken.apikey}")
    private String apiKey;

    @Value("${exchanges.kraken.secretkey}")
    private String secretKey;

    @Bean
    public KrakenExchange krakenExchange() {
        ExchangeSpecification spec = new ExchangeSpecification(KrakenExchange.class);
        spec.setApiKey(apiKey);
        spec.setSecretKey(secretKey);
        spec.setUserName(userName);

        KrakenExchange krakenExchange = (KrakenExchange) ExchangeFactory.INSTANCE.createExchange(spec);
        return krakenExchange;
    }

}
