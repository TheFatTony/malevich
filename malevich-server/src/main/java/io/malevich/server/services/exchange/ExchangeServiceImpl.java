package io.malevich.server.services.exchange;


import io.malevich.server.domain.BitcoinTransfersEntity;
import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.services.bitcointransfers.BitcoinTransfers;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
import io.malevich.server.services.payments.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.wallet.Wallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
public class ExchangeServiceImpl implements ExchangeService {


    @Autowired
    private BitcoinTransfers bitcoinTransfers;

    @Autowired
    private KrakenExchange krakenExchange;

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    @Autowired
    private PaymentsService paymentsService;

    protected ExchangeServiceImpl() {
    }

    @Override
    @Transactional
    public void placeOrders() throws IOException {
        List<FundingRecord> fundingRecords = krakenExchange.getAccountService().getFundingHistory(new DefaultTradeHistoryParamCurrency(Currency.BTC));

        for (FundingRecord fundingRecord : fundingRecords) {
            List<BitcoinTransfersEntity> bitcoinTransfersEntities = bitcoinTransfers.findByStatusAndAmount("SENT", fundingRecord.getAmount().add(new BigDecimal(0.001D)).multiply(new BigDecimal(100000000)).round(MathContext.DECIMAL32));

            for (BitcoinTransfersEntity bitcoinTransfersEntity : bitcoinTransfersEntities) {
//                if (bitcoinTransfersEntity.getAmount().equals(fundingRecord.getAmount())) {
                MarketOrder order = new MarketOrder((Order.OrderType.ASK), fundingRecord.getAmount(), CurrencyPair.BTC_EUR);
                String orderId = krakenExchange.getTradeService().placeMarketOrder(order);
                exchangeOrderService.save(order, bitcoinTransfersEntity.getPaymentMethod(), "Kraken", orderId);
                bitcoinTransfersEntity.setStatus("PROCESSED");
                bitcoinTransfers.save(bitcoinTransfersEntity);
                break;
//                }
            }
        }
    }

    @Override
    @Transactional
    public void placeOrder(Long balance, Wallet wallet, PaymentMethodEntity paymentMethodEntity) throws IOException, InterruptedException, InsufficientMoneyException, ExecutionException {
        MarketOrder order = new MarketOrder((Order.OrderType.ASK), new BigDecimal(balance), CurrencyPair.BTC_EUR);
        String orderId = krakenExchange.getTradeService().placeMarketOrder(order);
        exchangeOrderService.save(order, paymentMethodEntity, "Kraken", orderId);
    }

    @Override
    @Transactional
    public void processExecution() throws IOException {
        UserTrades userTrades = krakenExchange.getTradeService().getTradeHistory(new DefaultTradeHistoryParamCurrency(Currency.BTC));

        for (UserTrade trade : userTrades.getUserTrades()) {
            ExchangeOrderEntity exchangeOrderEntity = exchangeOrderService.findByOrderId(trade.getOrderId());
            if ((exchangeOrderEntity != null) && (ExchangeOrderStatus.SUBMITTED.equals(exchangeOrderEntity.getInternalStatus()))) {

                PaymentsEntity paymentsEntity = new PaymentsEntity();
                paymentsEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
                paymentsEntity.setAmount(trade.getOriginalAmount());
                paymentsEntity.setPaymentMethod(exchangeOrderEntity.getPaymentMethod());
                paymentsEntity.setParticipant(exchangeOrderEntity.getPaymentMethod().getParticipant());

                paymentsService.insertAdmin(paymentsEntity);

                exchangeOrderEntity.setInternalStatus(ExchangeOrderStatus.EXECUTED);
                exchangeOrderService.save(exchangeOrderEntity);
            }
        }

    }

}
