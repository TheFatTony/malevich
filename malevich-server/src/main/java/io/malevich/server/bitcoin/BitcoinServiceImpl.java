package io.malevich.server.bitcoin;


import io.malevich.server.blockonomics.model.BalanceResponseModel;
import io.malevich.server.blockonomics.services.balance.BalanceService;
import io.malevich.server.domain.BitcoinTransfersEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.services.bitcointransfers.BitcoinTransfers;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
public class BitcoinServiceImpl implements BitcoinService {

    @Value("${malevich.kraken.wallet.address}")
    private String krakenWallet;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;


    @Autowired
    private BalanceService balanceService;

    @Autowired
    private BitcoinTransfers bitcoinTransfers;

    @Autowired
    private BlockChain blockChain;

    private long catchupTime = System.currentTimeMillis() / 1000 - 60 * 60 * 6;


    protected BitcoinServiceImpl() {
    }


    @Override
    @Transactional
    public void checkBalance() throws UnreadableWalletException, InterruptedException, InsufficientMoneyException, ExecutionException, IOException, BlockStoreException {
        List<PaymentMethodBitcoinEntity> accounts = paymentMethodBitcoinService.findAllAll();

        for (PaymentMethodBitcoinEntity account : accounts) {

            BalanceResponseModel balance = balanceService.get(account);

            log.info("address = " + balance.getResponse().get(0).getAddress() + " balance = " + balance.getResponse().get(0).getConfirmed().toString());

            if (balance.getResponse().get(0).getConfirmed() > 0) {
                sendCoins(account.getBtcWallet(), krakenWallet, balance.getResponse().get(0).getConfirmed());
                BitcoinTransfersEntity bitcoinTransfersEntity = new BitcoinTransfersEntity();
                bitcoinTransfersEntity.setAmount(new BigDecimal(balance.getResponse().get(0).getConfirmed()));
                bitcoinTransfersEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
                bitcoinTransfersEntity.setDestinationAddress(krakenWallet);
                bitcoinTransfersEntity.setSenderAddress(balance.getResponse().get(0).getAddress());
                bitcoinTransfersEntity.setPaymentMethod(account);
                bitcoinTransfersEntity.setStatus("SENT");
                bitcoinTransfers.save(bitcoinTransfersEntity);
            }

            ByteArrayOutputStream walletDump = new ByteArrayOutputStream();
            account.getBtcWallet().saveToFileStream(walletDump);
            account.setWallet(walletDump.toByteArray());
            paymentMethodBitcoinService.save(account);
        }
    }


    @Override
    public Transaction sendCoins(Wallet wallet, String destinationAddress, long satoshis) throws InsufficientMoneyException, ExecutionException, InterruptedException, BlockStoreException {
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.start();
        peerGroup.setFastCatchupTimeSecs(catchupTime);
        catchupTime = System.currentTimeMillis() / 1000 - 60 * 5;
        peerGroup.addWallet(wallet);
        blockChain.addWallet(wallet);
        peerGroup.downloadBlockChain();

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
        peerGroup.stop();
        return endTransaction;
    }

}
