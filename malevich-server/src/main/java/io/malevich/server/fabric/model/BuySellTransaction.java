package io.malevich.server.fabric.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuySellTransaction {

    private String $class;

    private String id;

    private String effectiveDate;

    private String partyId;

    private String counterpartyId;

    private String artworkStockId;

    private String amount;

    private String quantity;

    public BuySellTransaction(String id,
                              String effectiveDate,
                              String partyId,
                              String counterpartyId,
                              String artworkStockId,
                              String amount,
                              String quantity) {
        this.$class = "io.malevich.network.BuySellTransaction";
        this.id = id;
        this.effectiveDate = effectiveDate;
        this.partyId = partyId;
        this.counterpartyId = counterpartyId;
        this.artworkStockId = artworkStockId;
        this.amount = amount;
        this.quantity = quantity;
    }
}
