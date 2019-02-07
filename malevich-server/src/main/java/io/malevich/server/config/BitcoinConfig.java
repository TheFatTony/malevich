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
        return NetworkParameters.testNet3();
    }

    @Bean
    public MemoryBlockStore memoryBlockStore() {
        return new MemoryBlockStore(networkParameters());
    }

    @Bean
    public Context context() {
        return new Context(networkParameters());
    }


//    @Bean
//    public WalletAppKit walletAppKit() throws UnreadableWalletException {
//        WalletAppKit kit = new WalletAppKit(context(), new File("."), "malevich-btc");
//        kit.startAsync();
//        kit.awaitRunning();
//        kit.peerGroup().addPeerDiscovery(new DnsDiscovery(networkParameters()));
//
//        kit.peerGroup().setFastCatchupTimeSecs((System.currentTimeMillis() / 1000));
//
//
//        return kit;
//    }


}
