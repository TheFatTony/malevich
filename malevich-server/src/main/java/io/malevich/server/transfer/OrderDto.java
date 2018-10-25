package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
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

}
