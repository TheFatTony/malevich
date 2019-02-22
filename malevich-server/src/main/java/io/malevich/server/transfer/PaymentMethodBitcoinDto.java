package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodBitcoinDto extends PaymentMethodDto{
    private String btcAddress;
}
