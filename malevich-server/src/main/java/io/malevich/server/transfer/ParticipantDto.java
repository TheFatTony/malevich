package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class ParticipantDto {

    private Long id;

    private String phoneNumber;

    private CountryDto country;

    private List<UserDto> users;

    private List<AddressDto> addresses;

}