package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeHistoryAsset {

    private String id;

    private OrderConcept askOrder;

    private OrderConcept bidOrder;


}
