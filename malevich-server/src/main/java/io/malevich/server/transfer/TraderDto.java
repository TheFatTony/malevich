package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TraderDto {

    private PersonDto person;

    private UserDto user;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp dateOfBirth;

    private char gender;

    private FileDto thumbnail;

}