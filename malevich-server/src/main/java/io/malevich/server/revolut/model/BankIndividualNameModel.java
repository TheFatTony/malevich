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
public class BankIndividualNameModel {



    @JsonProperty("first_name")
    private String firstName;


    @JsonProperty("last_name")
    private String lastName;

}
