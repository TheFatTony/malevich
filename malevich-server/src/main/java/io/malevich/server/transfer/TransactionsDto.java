package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionsDto {

  private long id;

  private TransactionTypeDto type;

  private java.sql.Timestamp effectiveDate;

  private CounterpartyDto party;

  private CounterpartyDto counterparty;

  private ArtworkStockDto artworkStock;

  private double amount;

  private int quantity;

}
