package io.malevich.server.rest.resources;


import com.google.common.collect.Lists;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.revolut.model.TransactionLegModel;
import io.malevich.server.revolut.model.TransactionModel;
import io.malevich.server.revolut.services.transactions.TransactionsBankService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.services.revoluttransaction.RevolutTransactionService;
import io.malevich.server.services.revoluttransaction.RevolutTransactionServiceImpl;
import io.malevich.server.services.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    @Autowired
    RevolutTransactionServiceImpl revolutTransactionService;

//    https://coinfaucet.eu/en/btc-testnet/

    @Autowired
    SmsService smsService;

    @RequestMapping(value = "/smsService", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> smsService() {
        smsService.sendSms("+14159352345", "Sample Twilio SMS using Java");

        return ResponseEntity.ok().build();
    }

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

    @RequestMapping(value = "/testRevolutTransactions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> testRevolutTransactions() {

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId(UUID.randomUUID().toString());
        transactionModel.setType("topup");
        transactionModel.setState("completed");
        transactionModel.setReference("Hello! VC3O BUOF DZSO W4OM malevich.iosome words");

        TransactionLegModel legModel = new TransactionLegModel();
        legModel.setId(UUID.randomUUID().toString());
        legModel.setAccountId(UUID.randomUUID().toString());
        legModel.setAmount(BigDecimal.valueOf(10001));
        legModel.setCurrency("EUR");
        legModel.setDescription("It is description");

        transactionModel.setLegs(Lists.newArrayList(legModel));

        revolutTransactionService.processRevolutTopUpTransaction(transactionModel);

        return ResponseEntity.ok().body("");
    }


}
