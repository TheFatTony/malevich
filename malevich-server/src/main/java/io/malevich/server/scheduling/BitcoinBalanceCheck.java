package io.malevich.server.scheduling;

import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BitcoinBalanceCheck {


    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private ExchangeOrderService exchangeOrderService;

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

    private void placeOrder(Wallet wallet, PaymentMethodEntity paymentMethodEntity) {
        MarketOrder order = new MarketOrder((Order.OrderType.ASK), new BigDecimal(wallet.getBalance().getValue()), CurrencyPair.BTC_EUR);
        String orderReturnValue = null;
        try {
            orderReturnValue = krakenExchange.getTradeService().placeMarketOrder(order);
            saveOrder(order, paymentMethodEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Limit Order return value: " + orderReturnValue);
    }

    public void saveOrder(Order order, PaymentMethodEntity paymentMethodEntity) {
        ExchangeOrderEntity entity = new ExchangeOrderEntity();
        entity.setPaymentMethod(paymentMethodEntity);
        entity.setExchangeName(krakenExchange.getExchangeSpecification().getExchangeName());
        entity.setInternalStatus(ExchangeOrderStatus.SUBMITTED);
        entity.setType(order.getType().name());
        entity.setOriginalAmount(order.getOriginalAmount());
        entity.setCurrencyPair(order.getCurrencyPair().toString());
        entity.setOrderId(order.getId());
        entity.setTimestamp(order.getTimestamp() != null ? new Timestamp(order.getTimestamp().getTime()) : null);
        entity.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        entity.setCumulativeAmount(order.getCumulativeAmount());
        entity.setAveragePrice(order.getAveragePrice());
        entity.setFee(order.getFee());
        entity.setLeverage(order.getLeverage());
        entity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));

        exchangeOrderService.save(entity);

    }

}
