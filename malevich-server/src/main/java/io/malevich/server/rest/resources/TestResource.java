package io.malevich.server.rest.resources;


import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.scheduling.BitcoinBalanceCheck;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestResource {

    private static Wallet wallet;
    @Autowired
    NetworkParameters networkParameters;
    @Autowired
    MemoryBlockStore memoryBlockStore;
    @Autowired
    KrakenExchange krakenExchange;

    @Autowired
    PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    PaymentMethodDao paymentMethodDao;

    @Autowired
    BitcoinBalanceCheck bitcoinBalanceCheck;

    @RequestMapping(value = "/wallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> wallet() {
        wallet = new Wallet(networkParameters);
        BlockChain chain = null;
        try {
            chain = new BlockChain(networkParameters, wallet, memoryBlockStore);
            PeerGroup peerGroup = new PeerGroup(networkParameters, chain);
            peerGroup.addWallet(wallet);
            peerGroup.start();

            Address a = wallet.currentReceiveAddress();
            ECKey b = wallet.currentReceiveKey();
            Address c = wallet.freshReceiveAddress();

            assert b.toAddress(wallet.getParams()).equals(a);
            assert !c.equals(a);

            return ResponseEntity.ok().body(a.toString());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/ticker", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> ticker() {
        Ticker ticker = null;
        try {
            ticker = krakenExchange.getMarketDataService().getTicker(CurrencyPair.BTC_EUR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(ticker.toString());
    }


    @RequestMapping(value = "/addCoins", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> addCoins() throws InsufficientMoneyException {
        //https://coinfaucet.eu/en/btc-testnet/
//        Wallet wallet = new Wallet(networkParameters);
        Address a = new Address(networkParameters, "mtAwHN11WCquu9JL5bLVXo3vwxU7n2Z7p8");
        SendRequest req = SendRequest.to(a, Coin.parseCoin("0.12"));
//        req.aesKey = wallet.getKeyCrypter().deriveKey("password");
        wallet.sendCoins(req);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/saveExchangeOrder", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> saveExchangeOrder() {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodDao.findByType_Id("BTC").stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());

        for (PaymentMethodBitcoinEntity account : accounts) {
            Wallet wallet = null;
            try {
                wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(account.getWallet()));
                MarketOrder order = new MarketOrder(
                        (Order.OrderType.ASK),
                        new BigDecimal(wallet.getBalance().getValue()),
                        CurrencyPair.BTC_EUR
                );
                bitcoinBalanceCheck.saveOrder(order, account);
            } catch (UnreadableWalletException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().build();
    }


}
