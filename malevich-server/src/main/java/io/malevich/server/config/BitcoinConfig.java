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

    @Bean
    public SPVBlockStore memoryBlockStore() throws BlockStoreException {
        return new SPVBlockStore(networkParameters(), BLOCKCHAIN_FILE);
    }

    @Bean
    public Context context() {
        return new Context(networkParameters());
    }

    @Bean
    public BlockChain blockChain() throws BlockStoreException {
        return new BlockChain(context(), memoryBlockStore());
    }

    @Override
    public PeerGroup startPeerGroup() {
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.start();

        return peerGroup;
    }

}
