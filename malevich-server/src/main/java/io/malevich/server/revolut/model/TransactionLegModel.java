package io.malevich.server.revolut.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLegModel{

    @JsonProperty("leg_id")
    private String id;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("counterparty")
    private TransactionLegCounterpartyModel counterparty;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;
}
