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
        spec.setApiKey("ub2kSjRtW87oViLK1iZgpDznDeQjkm9j9iMFHuDvlAehlKERlrd1HdM4");
        spec.setSecretKey("CwU6v/UbnxNTgjAJ1RB3GZg1rcXC8lKNFp949YV+/gAICTvDvDXm0dsWEIzI2VcFkz6yW7/YkT+yAeJhl9ACGw==");
        spec.setUserName("Vivin24");

        KrakenExchange krakenExchange = (KrakenExchange) ExchangeFactory.INSTANCE.createExchange(spec);
        return krakenExchange;
    }

}
