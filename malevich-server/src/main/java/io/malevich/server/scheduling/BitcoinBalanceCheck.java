package io.malevich.server.scheduling;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.services.bitcoin.BitcoinService;
import io.malevich.server.services.exchange.ExchangeService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.services.payments.PaymentsService;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.PeerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Profile({"test", "prod"})
@Component
public class BitcoinBalanceCheck {


    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    public Context context;

    private BigDecimal exchangeRate = new BigDecimal("4000");

    @Autowired
    private PaymentsService paymentsService;

//    @Scheduled(initialDelay = 2000, fixedDelay = 10000)
    public void checkBalance() {
        Context.propagate(context);
        try {
            List<PaymentMethodBitcoinEntity> accounts = paymentMethodBitcoinService.findAllAll();

            PeerGroup peerGroup = bitcoinService.startPeerGroup();
            bitcoinService.downloadBlockchain(peerGroup);

            for (PaymentMethodBitcoinEntity account : accounts) {
//            wallet.addWatchedAddress(new Address(networkParameters, account.getBtcAddress()));
//            peerGroup.addWallet(wallet);

                if (account.getBtcWallet().getBalance().getValue() > 0) {
                    // real code
//                    exchangeService.placeOrder(account.getBtcWallet(), account);

                    // current mock begin
                    PaymentsEntity paymentsEntity = new PaymentsEntity();
                    paymentsEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
                    paymentsEntity.setAmount(new BigDecimal(account.getBtcWallet().getBalance().getValue()).multiply(exchangeRate));
                    paymentsEntity.setPaymentMethod(account);
                    paymentsEntity.setParticipant(account.getParticipant());
                    paymentsService.insert(paymentsEntity);
                    // current mock end
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
