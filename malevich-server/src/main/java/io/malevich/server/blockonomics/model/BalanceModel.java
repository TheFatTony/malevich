package io.malevich.server.blockonomics.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceModel {

    @JsonProperty("confirmed")
    private Long confirmed;

    @JsonProperty("addr")
    private String address;

    @JsonProperty("unconfirmed")
    private Long unconfirmed;

}
