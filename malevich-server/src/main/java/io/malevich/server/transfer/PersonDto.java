package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp dateOfBirth;

    private GenderDto gender;

    private FileDto thumbnail;

}
