package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDto {


  private long id;

  private String typeId;

  private long artworkId;

  private long partyId;

  private String tradeTypeId;

  private double amount;

  private java.sql.Timestamp effectiveDate;

  private long transactionId;

}
