package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDto {

    private Long id;

    private OrderTypeDto type;

    private ArtworkStockDto artworkStock;

    private CounterpartyDto party;

    private TradeTypeDto tradeType;

    private Double amount;

    private java.sql.Timestamp effectiveDate;

    private Double bestBid;

    private Double currentAsk;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private java.sql.Timestamp expirationDate;

}
