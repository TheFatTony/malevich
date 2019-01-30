package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentMethodDto {


    private Long id;

    private PaymentMethodTypeDto type;

//    private Object payload;

    private String cardNumber;
    private String cardHolder;
    private String cardExpiration;
    private String cardCvv;

}
