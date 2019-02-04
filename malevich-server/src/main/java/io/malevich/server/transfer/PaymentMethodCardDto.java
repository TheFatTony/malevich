package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodCardDto extends PaymentMethodDto{
    private String cardNumber;
    private String cardHolder;
    private String cardExpiration;
    private String cardCvv;
}
