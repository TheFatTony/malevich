package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yinyang.core.server.core.dto.View;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDto {

    @JsonView({View.Admin.class, View.Gallery.class, View.Trader.class})
    private String id;

    private OrderTypeDto type;

    private ArtworkStockDto artworkStock;

    private ParticipantDto party;

    private TradeTypeDto tradeType;

    private Double amount;

    @JsonView({View.Admin.class})
    private java.sql.Timestamp effectiveDate;

    private Double bestBid;

    private Double currentAsk;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private java.sql.Timestamp expirationDate;

}
