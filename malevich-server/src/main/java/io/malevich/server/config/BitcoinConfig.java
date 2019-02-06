package io.malevich.server.config;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class BitcoinConfig {


    @Autowired
    private PaymentMethodDao paymentMethodDao;


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


    @Bean
    public WalletAppKit walletAppKit() throws UnreadableWalletException {
        WalletAppKit kit = new WalletAppKit(context(), new File("."), "malevich-btc");
        kit.startAsync();
        kit.awaitRunning();
        kit.peerGroup().addPeerDiscovery(new DnsDiscovery(networkParameters()));
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
            wallet.addWatchedAddress(new Address(networkParameters(), account.getBtcAddress()));
            kit.peerGroup().addWallet(wallet);
        }
        kit.peerGroup().setFastCatchupTimeSecs((System.currentTimeMillis() / 1000));


        return kit;
    }


}
