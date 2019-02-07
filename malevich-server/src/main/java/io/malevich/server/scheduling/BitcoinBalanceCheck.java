package io.malevich.server.scheduling;

import io.malevich.server.config.Constants;
import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
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
import java.io.File;
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
    private MemoryBlockStore memoryBlockStore;

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private KrakenExchange krakenExchange;


    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void checkBalance() throws UnreadableWalletException {
        WalletAppKit kit = null;

        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        if (accounts.size() > 0) {
            kit = new WalletAppKit(networkParameters, new File("."), "malevich-btc");
            kit.startAsync();
            kit.awaitRunning();
        }

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
            wallet.addWatchedAddress(new Address(networkParameters, account.getBtcAddress()));
            BlockChain chain;
            try {
                chain = new BlockChain(networkParameters, wallet, memoryBlockStore);

                PeerGroup peerGroup = new PeerGroup(networkParameters, chain);
                peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
                peerGroup.addWallet(wallet);
                peerGroup.setFastCatchupTimeSecs(Constants.APPLICATION_RUN_TIME);
                peerGroup.start();
                peerGroup.downloadBlockChain();
                System.out.println("!!!! Fucking balance = " + wallet.getBalance());
                peerGroup.stop();
            } catch (BlockStoreException e) {
                e.printStackTrace();
            }
            if (accounts.size() > 0) {
                kit.stopAsync();
                kit.awaitTerminated();
            }
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
