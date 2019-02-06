package io.malevich.server.scheduling;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitcoinBalanceCheck {


    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private KrakenExchange krakenExchange;

    @Autowired
    private WalletAppKit walletAppKit;

    @Autowired
    private Context context;

    @Autowired
    private MemoryBlockStore memoryBlockStore;


//    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkBalance() throws UnreadableWalletException {
        Context.propagate(context);
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
            walletAppKit.peerGroup().downloadBlockChain();

            System.out.println("!!!! Fucking balance = " + wallet.getBalance());
        }

    }

    private void placeOrder(Wallet wallet) {
        MarketOrder limitOrder = new MarketOrder((Order.OrderType.ASK), new BigDecimal(wallet.getBalance().getValue()), CurrencyPair.BTC_EUR);
        String limitOrderReturnValue = null;
        try {
            limitOrderReturnValue = krakenExchange.getTradeService().placeMarketOrder(limitOrder);
            // TODO save this crap
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Limit Order return value: " + limitOrderReturnValue);
    }

}
