package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DelayedChangeDto {


  private Long id;

  private String typeId;

  private Object payload;

}
