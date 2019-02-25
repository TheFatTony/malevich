package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDepositReferenceDto extends PaymentMethodDto{
    private String reference;

}
