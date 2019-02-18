package io.malevich.server.revolut.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("reason_code")
    private String reasonCode;

    @JsonProperty("created_at")
    private Timestamp created;

    @JsonProperty("completed_at")
    private Timestamp completed;
}
