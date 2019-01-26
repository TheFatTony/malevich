package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeHistoryAsset {

    private String id;

    private String effectiveDate;

    private String artworkStock;

    private OrderAsset askOrder;

    private OrderAsset bidOrder;


}
