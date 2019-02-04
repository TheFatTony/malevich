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
import org.knowm.xchange.dto.trade.OpenOrders;
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
public class MarketOrdersCheck {


    @Autowired
    MemoryBlockStore memoryBlockStore;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private KrakenExchange krakenExchange;




//    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkExecution() throws IOException {

         OpenOrders openOrders = krakenExchange.getTradeService().getOpenOrders();

         for (Order order: openOrders.getAllOpenOrders()) {

         }

    }

}
