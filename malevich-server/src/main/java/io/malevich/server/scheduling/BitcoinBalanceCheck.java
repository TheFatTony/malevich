package io.malevich.server.scheduling;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
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
    MemoryBlockStore memoryBlockStore;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private KrakenExchange krakenExchange;




//    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkBalance() throws UnreadableWalletException, BlockStoreException {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));

            BlockChain chain = new BlockChain(networkParameters, wallet, memoryBlockStore);

            final PeerGroup peerGroup = new PeerGroup(networkParameters, chain);
            peerGroup.startAsync();

            peerGroup.downloadBlockChain();
            peerGroup.stopAsync();

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
