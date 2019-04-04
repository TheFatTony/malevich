package io.malevich.server.config;

import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.store.MemoryBlockStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BitcoinConfig {

    @Bean
    public NetworkParameters networkParameters() {
        return NetworkParameters.prodNet();
    }

    @Bean
    public Context context() {
        return new Context(networkParameters());
    }

    @Bean
    public MemoryBlockStore memoryBlockStore() {
        return new MemoryBlockStore(networkParameters());
    }


}
