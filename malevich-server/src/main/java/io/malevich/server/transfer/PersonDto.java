package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PersonDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    private String mobile;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp dateOfBirth;

    private GenderDto gender;

    private CountryDto country;

    private List<AddressDto> addresses;

}
