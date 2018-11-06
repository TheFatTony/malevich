package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TraderDto {

    private Long id;

    private PersonDto person;

    private UserDto user;

    private String mobile;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
    private Timestamp dateOfBirth;

    private GenderDto gender;

    private CountryDto country;

    private FileDto thumbnail;

    private List<AddressDto> addresses;

}