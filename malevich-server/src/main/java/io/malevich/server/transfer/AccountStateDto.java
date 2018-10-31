package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountStateDto {


  private Long id;

  private CounterpartyDto party;

  private ArtworkStockDto artworkStock;

  private Long quantity;

  private Double amount;

}
