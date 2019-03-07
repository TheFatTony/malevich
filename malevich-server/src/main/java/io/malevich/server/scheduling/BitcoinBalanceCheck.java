package io.malevich.server.scheduling;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import io.malevich.server.services.bitcoin.BitcoinService;
import io.malevich.server.services.exchange.ExchangeService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.services.payments.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class BitcoinBalanceCheck {


    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private NetworkParameters networkParameters;

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
                peerGroup.addWallet(account.getBtcWallet());
                txHistory(account.getBtcWallet());
                log.info("!!!! wallet = " + account.getBtcAddress() + "balance = "+ account.getBtcWallet().getBalance().getValue());

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

    private void txHistory(Wallet wallet)
    {
        Set<Transaction> txx = wallet.getTransactions(true);
        if (!txx.isEmpty())
        {
            int i = 1;
            for (Transaction tx : txx)
            {
                System.out.println(i + "  ________________________");
                System.out.println("Date and Time: " + tx.getUpdateTime().toString());
                System.out.println("From Address: " + tx.getOutput(0).getAddressFromP2PKHScript(networkParameters));
                System.out.println("To Address: " + tx.getOutput(0).getAddressFromP2PKHScript(networkParameters));
                System.out.println("Amount Sent to me: " + tx.getValueSentToMe(wallet).toFriendlyString());
                System.out.println("Amount Sent from me: " + tx.getValueSentFromMe(wallet).toFriendlyString());
                long fee = (tx.getInputSum().getValue() > 0 ? tx.getInputSum().getValue() - tx.getOutputSum().getValue() : 0);
                System.out.println("Fee: " + Coin.valueOf(fee).toFriendlyString());
                System.out.println("Transaction Depth: " + tx.getConfidence().getDepthInBlocks());
                System.out.println("Transaction Blocks: " + tx.getConfidence().toString());
                System.out.println("Tx Hex: " + tx.getHashAsString());
                System.out.println("Tx: " + tx.toString());
                i++;
            }
        }
        else
        {
            System.err.println("No Transaction Found");
        }
    }

}
