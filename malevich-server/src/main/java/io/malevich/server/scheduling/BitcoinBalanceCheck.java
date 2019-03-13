package io.malevich.server.scheduling;

import io.malevich.server.bitcoin.BitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BitcoinBalanceCheck {

    @Autowired
    private BitcoinService bitcoinService;


    @Scheduled(initialDelay = 2000, fixedDelay = 10000)
    public void checkBalance() {
        try {
            bitcoinService.checkBalance();
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
