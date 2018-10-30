package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDto {


    private long id;

    private OrderTypeDto type;

    private ArtworkStockDto artworkStock;

    private CounterpartyDto party;

    private TradeTypeDto tradeType;

    private double amount;

    private java.sql.Timestamp effectiveDate;

    private double bestBid;

    private double currentAsk;

}
