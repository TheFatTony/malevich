package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TraderDto {

    private PersonDto person;

    private UserDto user;

    private String mobile;

    private Date dateOfBirth;

    private char gender;

    private FileDto thumbnail;

}