package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "payment_method_card")
public class PaymentMethodCardEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "number")
    private String cardNumber;

    @Getter
    @Setter
    @Column(name = "holder")
    private String cardHolder;

    @Getter
    @Setter
    @Column(name = "expiration")
    private String cardExpiration;

    @Getter
    @Setter
    @Column(name = "cvv")
    private String cardCvv;

}
