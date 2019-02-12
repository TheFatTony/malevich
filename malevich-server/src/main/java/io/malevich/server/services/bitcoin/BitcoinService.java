package io.malevich.server.services.bitcoin;


import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface BitcoinService {


    PeerGroup startPeerGroup();

    void downloadBlockchain(PeerGroup peerGroup);

    Transaction sendCoins(Wallet wallet, String destinationAddress, long satoshis) throws InsufficientMoneyException, ExecutionException, InterruptedException;
}
