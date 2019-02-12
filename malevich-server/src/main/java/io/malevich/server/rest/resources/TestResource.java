package io.malevich.server.rest.resources;


import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.scheduling.BitcoinBalanceCheck;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrency;
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

    @Autowired
    KrakenExchange krakenExchange;

    @Autowired
    PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    PaymentMethodDao paymentMethodDao;

//    @Autowired
//    BitcoinBalanceCheck bitcoinBalanceCheck;



    @RequestMapping(value = "/viewExchangeOrders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> viewExchangeOrders() {
        UserTrades userTrades = null;
        String result = null;
        try {
            userTrades = krakenExchange.getTradeService().getTradeHistory(new DefaultTradeHistoryParamCurrency(Currency.BTC));

            for (UserTrade trade : userTrades.getUserTrades()) {
                if ("OPAVLR-DLYUU-BKMVFS".equals(trade.getOrderId())) {
                    result = trade.toString();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/testExchangeOrder", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> testExchangeOrder() {
        String result = null;
        try {
            MarketOrder order = new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.002"), CurrencyPair.BTC_EUR);
            result = krakenExchange.getTradeService().placeMarketOrder(order);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(result);
    }


}
