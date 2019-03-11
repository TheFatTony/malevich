package io.malevich.server.blockonomics.model;

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
public class NewAddressModel {

    @JsonProperty("addr")
    private String address;

    @JsonProperty("tag")
    private String tag;

}
