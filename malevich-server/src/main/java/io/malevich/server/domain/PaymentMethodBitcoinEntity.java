package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.bitcoinj.core.Address;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.ByteArrayInputStream;


@Entity
@Table(name = "payment_method_bitcoin")
public class PaymentMethodBitcoinEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "address")
    private String btcAddress;

    @Getter
    @Setter
    @Column(name = "wallet")
    private byte[] wallet;


    @Transient
    private Wallet btcWallet;


    public Wallet getBtcWallet() throws UnreadableWalletException {
        if (btcWallet == null) {
            btcWallet = Wallet.loadFromFileStream(new ByteArrayInputStream(wallet));
        }
        return btcWallet;
    }
}
