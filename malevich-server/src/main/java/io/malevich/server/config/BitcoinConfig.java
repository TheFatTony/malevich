package io.malevich.server.config;

import org.bitcoinj.core.NetworkParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BitcoinConfig {

    @Bean
    public NetworkParameters networkParameters() {
        return NetworkParameters.prodNet();
    }


}
