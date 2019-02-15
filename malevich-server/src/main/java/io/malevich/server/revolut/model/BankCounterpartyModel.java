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
public class BankCounterpartyModel {

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("individual_name")
    private BankIndividualNameModel individualName;

    @JsonProperty("bank_country")
    private String bankCountry;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("bic")
    private String bic;


}
