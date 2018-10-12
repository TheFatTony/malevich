package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CounterpartyDto {


  private long id;

  private String typeId;

  private long traderId;

  private long galleryId;

}
