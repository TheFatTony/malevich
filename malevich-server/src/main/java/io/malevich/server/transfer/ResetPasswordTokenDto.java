package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResetPasswordTokenDto {


  private long id;

  private String token;

  private long userId;

  private java.sql.Timestamp effectiveDate;

}
