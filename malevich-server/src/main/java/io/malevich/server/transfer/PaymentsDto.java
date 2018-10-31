package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentsDto {


  private Long id;

  private java.sql.Timestamp effectiveDate;

  private PaymentMethodDto paymentMethod;

  private Double amount;

}
