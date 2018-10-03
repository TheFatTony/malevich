package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTokenDto {

    private Long id;

    private String token;

    private String userName;

    private UserTypeDto userType;

    private java.sql.Timestamp effectiveDate;

}
