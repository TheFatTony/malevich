package io.malevich.server.bitcoin;


import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface BitcoinService {

    void downloadBlockchain();

    Transaction sendCoins(Wallet wallet, String destinationAddress, long satoshis) throws InsufficientMoneyException, ExecutionException, InterruptedException;
}
