package io.malevich.server.bitcoin;


import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Slf4j
@Service
public class BitcoinServiceImpl implements BitcoinService {

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PeerGroup peerGroup;

    private long nextChainScanTime = System.currentTimeMillis() / 1000 - 86400;


    protected BitcoinServiceImpl() {
    }

    @Override
    public void downloadBlockchain() {
        peerGroup.setFastCatchupTimeSecs(nextChainScanTime);
        peerGroup.downloadBlockChain();
        nextChainScanTime = System.currentTimeMillis() / 1000 - 10;
    }


    @Override
    public Transaction sendCoins(Wallet wallet, String destinationAddress, long satoshis) throws InsufficientMoneyException, ExecutionException, InterruptedException {
        Address dest = Address.fromBase58(networkParameters, destinationAddress);
        SendRequest request = null;
        Wallet.SendResult result;

        try {
            request = SendRequest.to(dest, Coin.valueOf(satoshis - Transaction.DEFAULT_TX_FEE.getValue()));
            result = wallet.sendCoins(request);
        } catch (InsufficientMoneyException e) {
            request = SendRequest.to(dest, Coin.valueOf(satoshis - Transaction.REFERENCE_DEFAULT_MIN_TX_FEE.getValue()));
            result = wallet.sendCoins(request);
        }


        Transaction endTransaction = result.broadcastComplete.get();
        // TODO dump wallet for the fucks sake
        return endTransaction;
    }

}
