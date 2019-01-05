package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class PaymentTypeDto {


    private String id;

    private Map<String, String> nameMl;

}
