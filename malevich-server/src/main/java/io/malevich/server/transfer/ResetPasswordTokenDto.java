package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResetPasswordTokenDto {

  private String token;

  private String password;

}
