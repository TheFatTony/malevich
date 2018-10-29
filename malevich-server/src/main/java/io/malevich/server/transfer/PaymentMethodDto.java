package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class PaymentMethodDto {


  private Long id;

  private PaymentMethodTypeDto type;

  private Map<String, String> payload;

}
