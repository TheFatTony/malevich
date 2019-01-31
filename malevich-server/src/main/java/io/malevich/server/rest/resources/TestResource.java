package io.malevich.server.rest.resources;


import io.malevich.server.services.trader.TraderService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestResource {

    @Autowired
    NetworkParameters networkParameters;

    @Autowired
    BlockChain blockChain;

    @Autowired
    Wallet wallet;

    @Autowired
    KrakenExchange krakenExchange;


    @RequestMapping(value = "/wallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> wallet() {

        Address a = wallet.currentReceiveAddress();
        ECKey b = wallet.currentReceiveKey();
        Address c = wallet.freshReceiveAddress();

        assert b.toAddress(wallet.getParams()).equals(a);
        assert !c.equals(a);

        return ResponseEntity.ok().body(a.toString());
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


}
