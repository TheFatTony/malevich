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
        spec.setApiKey("XSMHQER81ZsOKjwfEOChDcGI6ELEAmj7QN5K3n4xdZOF675B99gHJM7F");
        spec.setSecretKey("iYc+kxtoVC/BQFlPpxz8XTLUkrU2IVWDzikYbQhMzdEZaeba0OKthnu2NGlb/S9xqU8RWBPw9/jbOTi3c8A9ag==");
        spec.setUserName("Malevich");

        KrakenExchange krakenExchange = (KrakenExchange) ExchangeFactory.INSTANCE.createExchange(spec);
        return krakenExchange;
    }

}
