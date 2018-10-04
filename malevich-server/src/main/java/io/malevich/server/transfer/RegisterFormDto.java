package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterFormDto {

    private String username;

    private UserTypeDto userType;

}
