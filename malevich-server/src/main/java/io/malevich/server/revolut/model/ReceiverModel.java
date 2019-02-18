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
public class ReceiverModel {

    @JsonProperty("counterparty_id")
    private String counterpartyId;

    @JsonProperty("account_id")
    private String accountId;
}
