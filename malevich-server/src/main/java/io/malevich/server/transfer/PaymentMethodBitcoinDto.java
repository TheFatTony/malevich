package io.malevich.server.transfer;

import io.malevich.server.domain.PaymentMethodDepositReferenceEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodBitcoinDto extends PaymentMethodDto{
    private String btcAddress;
}

