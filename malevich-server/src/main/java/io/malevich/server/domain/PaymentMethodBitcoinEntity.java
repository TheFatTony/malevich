package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


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


}
