package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private long id;

    private String street;

    private String postalCode;

    private String state;

    private String city;

    private CountryDto country;

}
