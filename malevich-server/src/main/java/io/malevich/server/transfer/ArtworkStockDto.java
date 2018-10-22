package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArtworkStockDto {


  private long id;

  private ArtworkDto artwork;

  private GalleryDto gallery;

  private double price;

}
