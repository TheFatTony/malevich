package io.malevich.server.bitcoin;


import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public interface BitcoinService {

    void checkBalance() throws UnreadableWalletException, InterruptedException, InsufficientMoneyException, ExecutionException, IOException;

    Transaction sendCoins(Wallet wallet, String destinationAddress, long satoshis) throws InsufficientMoneyException, ExecutionException, InterruptedException;
}
