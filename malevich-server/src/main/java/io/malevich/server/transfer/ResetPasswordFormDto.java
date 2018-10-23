package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordFormDto {

    private String lang;
    private String email;

}
