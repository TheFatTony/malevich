package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class TraderDto {

    private long id;

    private PersonDto person;

    private UserDto user;

    private String mobile;

    private Date dateOfBirth;

    private GenderDto gender;

    private CountryDto country;

    private FileDto thumbnail;

    private List<AddressDto> addresses;

}