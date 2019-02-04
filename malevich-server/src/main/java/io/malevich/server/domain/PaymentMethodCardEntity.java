package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "payment_method_card")
public class PaymentMethodCardEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "number")
    @Pattern(regexp = "[0-9]{12,19}")
    private String cardNumber;

    @Getter
    @Setter
    @Column(name = "holder")
    @Pattern(regexp = "[ A-Z]+")
    private String cardHolder;

    @Getter
    @Setter
    @Column(name = "expiration")
    @Pattern(regexp = "[0-1][0-9]/[1-9][0-9]")
    private String cardExpiration;

    @Getter
    @Setter
    @Column(name = "cvv")
    @Pattern(regexp = "[0-9]{3,}")
    private String cardCvv;

}
