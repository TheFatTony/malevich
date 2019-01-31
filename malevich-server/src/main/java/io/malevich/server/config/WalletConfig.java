package io.malevich.server.config;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WalletConfig {


    @Bean
    public NetworkParameters networkParameters() {
        return NetworkParameters.testNet2();
    }

    @Bean
    public Wallet wallet() {
        return new Wallet(networkParameters());
    }


    @Bean
    public BlockChain blockChain() throws BlockStoreException {
        return new BlockChain(networkParameters(), wallet(), new MemoryBlockStore(networkParameters()));
    }


    @Bean
    public PeerGroup peerGroup() throws BlockStoreException {
        PeerGroup peerGroup = new PeerGroup(networkParameters(), blockChain());
        peerGroup.addWallet(wallet());
        peerGroup.start();
        return peerGroup;
    }


}
