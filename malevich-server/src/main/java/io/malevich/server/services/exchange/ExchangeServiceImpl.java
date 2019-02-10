package io.malevich.server.services.exchange;


import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;


@Slf4j
@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private KrakenExchange krakenExchange;

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    protected ExchangeServiceImpl() {
    }

    private void placeOrder(Wallet wallet, PaymentMethodEntity paymentMethodEntity) throws IOException {
        MarketOrder order = new MarketOrder((Order.OrderType.ASK), new BigDecimal(wallet.getBalance().getValue()), CurrencyPair.BTC_EUR);
        String orderId = krakenExchange.getTradeService().placeMarketOrder(order);
        exchangeOrderService.save(order, paymentMethodEntity, "Kraken", orderId);
    }

}
