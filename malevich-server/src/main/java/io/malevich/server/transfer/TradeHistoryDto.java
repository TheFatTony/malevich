package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TradeHistoryDto {


  private long id;

  private ArtworkStockDto artworkStock;

  private java.sql.Timestamp effectiveDate;

  private OrderDto askOrder;

  private OrderDto bidOrder;

  private double amount;

  private long quantity;

}
