package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentMethodDto {


    private Long id;

    private PaymentMethodTypeDto type;

    private ParticipantDto participant;

}

