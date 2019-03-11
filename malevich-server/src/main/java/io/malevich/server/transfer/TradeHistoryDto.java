package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class TradeHistoryDto {


    private Long id;

    private ArtworkStockDto artworkStock;

    private Timestamp effectiveDate;

    private OrderDto askOrder;

    private OrderDto bidOrder;

    private Double amount;

    private Long quantity;

}
