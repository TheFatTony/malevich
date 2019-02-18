package io.malevich.server.revolut.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLegCounterpartyModel{
    @JsonProperty("id")
    private String id;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("account_id")
    private String accountId;
}
