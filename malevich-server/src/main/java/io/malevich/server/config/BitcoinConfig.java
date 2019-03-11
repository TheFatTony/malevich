package io.malevich.server.config;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;


@Configuration
public class BitcoinConfig {

    private static final File BLOCKCHAIN_FILE = new File("block.dat");


    @Bean
    public NetworkParameters networkParameters() {
        return NetworkParameters.testNet3();
    }





}
