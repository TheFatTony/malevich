package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParticipantDto {

    private Long id;

    private ParticipantTypeDto type;

    private String phoneNumber;

    private CountryDto country;

    private List<UserDto> users;

    private List<AddressDto> addresses;

}