package io.malevich.server.scheduling;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.services.bitcoin.BitcoinService;
import io.malevich.server.services.exchange.ExchangeService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import org.bitcoinj.core.PeerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class BitcoinBalanceCheck {


    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private ExchangeService exchangeService;

    //    @Scheduled(initialDelay = 2000, fixedDelay = 60000)
    public void checkBalance() {
        try {
            List<PaymentMethodBitcoinEntity> accounts = paymentMethodBitcoinService.findAll();

            PeerGroup peerGroup = bitcoinService.startPeerGroup();
            bitcoinService.downloadBlockchain(peerGroup);

            for (PaymentMethodBitcoinEntity account : accounts) {
//            wallet.addWatchedAddress(new Address(networkParameters, account.getBtcAddress()));
//            peerGroup.addWallet(wallet);

                if (account.getBtcWallet().getBalance().getValue() > 0) {
                    exchangeService.placeOrder(account.getBtcWallet(), account);
                }

                ByteArrayOutputStream walletDump = new ByteArrayOutputStream();
                account.getBtcWallet().saveToFileStream(walletDump);
                account.setWallet(walletDump.toByteArray());
                paymentMethodBitcoinService.save(account);

            }

            peerGroup.stop();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
