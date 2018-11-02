package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionsDto {

    private Long id;

    private TransactionTypeDto type;

    private java.sql.Timestamp effectiveDate;

    private CounterpartyDto party;

    private CounterpartyDto counterparty;

    private ArtworkStockDto artworkStock;

    private Double amount;

    private Integer quantity;

}
