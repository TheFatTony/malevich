package io.malevich.server.scheduling;

import io.malevich.server.blockonomics.model.BalanceResponseModel;
import io.malevich.server.blockonomics.services.balance.BalanceService;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.services.exchange.ExchangeService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BitcoinBalanceCheck {


    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private BalanceService balanceService;


    @Scheduled(initialDelay = 2000, fixedDelay = 10000)
    public void checkBalance() throws UnreadableWalletException, IOException, InterruptedException, InsufficientMoneyException, ExecutionException {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodBitcoinService.findAllAll();

        for (PaymentMethodBitcoinEntity account : accounts) {

            BalanceResponseModel balance = balanceService.get(account);

            log.info("address = " + balance.getResponse().get(0).getAddress() + " balance = " + balance.getResponse().get(0).getConfirmed().toString());

            if (balance.getResponse().get(0).getConfirmed() > 0) {
                exchangeService.placeOrder(balance.getResponse().get(0).getConfirmed(), account.getBtcWallet(), account);
            }

            ByteArrayOutputStream walletDump = new ByteArrayOutputStream();
            account.getBtcWallet().saveToFileStream(walletDump);
            account.setWallet(walletDump.toByteArray());
            paymentMethodBitcoinService.save(account);

        }

    }


}
