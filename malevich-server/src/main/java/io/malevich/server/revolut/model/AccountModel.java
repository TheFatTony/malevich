package io.malevich.server.revolut.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("type")
    private String type;

    @JsonProperty("account_no")
    private String accountNo;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("sort_code")
    private String sortCode;

    @JsonProperty("routing_number")
    private String routingNumber;

    @JsonProperty("bic")
    private String bic;

    @JsonProperty("recipient_charges")
    private String recipientCharges;

    @JsonProperty("bank_country")
    private String bankCountry;
}
