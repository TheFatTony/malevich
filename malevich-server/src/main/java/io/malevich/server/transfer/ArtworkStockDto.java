package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArtworkStockDto {


    private Long id;

    private ArtworkDto artwork;

    private GalleryDto gallery;

    private Double instantPrice;

    private Double bestBid;

    private Double lastPrice;

}
