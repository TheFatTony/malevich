package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.ByteArrayInputStream;


@Entity
@Table(name = "payment_method_deposit_reference")
public class PaymentMethodDepositReferenceEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "reference")
    private String reference;
}
