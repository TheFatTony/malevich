package io.malevich.server.scheduling;

import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitcoinBalanceCheck {


    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private BlockChain blockChain;

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    private long nextChainScanTime = System.currentTimeMillis() / 1000;


    @Scheduled(initialDelay = 2000, fixedDelay = 60000)
    public void checkBalance() throws UnreadableWalletException {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.setFastCatchupTimeSecs(nextChainScanTime);
        peerGroup.start();
        peerGroup.downloadBlockChain();
        nextChainScanTime = System.currentTimeMillis() / 1000 - 10;

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
            wallet.addWatchedAddress(new Address(networkParameters, account.getBtcAddress()));
//            peerGroup.addWallet(wallet);

            System.out.println("!!!! Fucking balance = " + wallet.getBalance());

            if (wallet.getBalance().getValue() > 0) {
//                    send(wallet, "mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB", wallet.getBalance().getValue());
//                        placeOrder
            }

            ByteArrayOutputStream walletDump = new ByteArrayOutputStream();
            try {
                wallet.saveToFileStream(walletDump);
                account.setWallet(walletDump.toByteArray());
                paymentMethodDao.save(account);
            } catch (IOException e) {
                new RuntimeException("Unable to save wallet seed");
            }

        }

        peerGroup.stop();
    }







}
