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

    @Autowired
    private KrakenExchange krakenExchange;

    private long nextChainScanTime = System.currentTimeMillis() / 1000;


    //    @Scheduled(initialDelay = 2000, fixedDelay = 60000)
    public void checkBalance() throws UnreadableWalletException {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.setFastCatchupTimeSecs(nextChainScanTime);
        peerGroup.start();
        peerGroup.downloadBlockChain();

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
            wallet.addWatchedAddress(new Address(networkParameters, account.getBtcAddress()));
            peerGroup.addWallet(wallet);

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

        nextChainScanTime = System.currentTimeMillis() / 1000 - 10;
        peerGroup.stop();
    }

    public Transaction send(Wallet wallet, String destinationAddress, long satoshis) throws Exception {
        Address dest = Address.fromBase58(networkParameters, destinationAddress);
        SendRequest request = null;
        Wallet.SendResult result;

        try {
            request = SendRequest.to(dest, Coin.valueOf(satoshis - Transaction.DEFAULT_TX_FEE.getValue()));
            result = wallet.sendCoins(request);
        } catch (Throwable e) {
            request = SendRequest.to(dest, Coin.valueOf(satoshis - Transaction.REFERENCE_DEFAULT_MIN_TX_FEE.getValue()));
            result = wallet.sendCoins(request);
        }


        Transaction endTransaction = result.broadcastComplete.get();
        // TODO dump wallet for the fucks sake
        return endTransaction;
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
