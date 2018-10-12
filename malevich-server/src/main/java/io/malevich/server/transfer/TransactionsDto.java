package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionsDto {


  private long id;

  private String typeId;

  private java.sql.Timestamp effectiveDate;

  private long partyId;

  private long counterpartyId;

  private long artworkId;

  private double amount;

  private long quantity;

}
