package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonView;
import com.yinyang.core.server.core.dto.View;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArtworkStockDto {

    private Long id;

    private ArtworkDto artwork;

    private GalleryDto gallery;

    @JsonView({View.Admin.class, View.Gallery.class, View.Trader.class})
    private Double instantPrice;

    @JsonView({View.Admin.class, View.Gallery.class, View.Trader.class})
    private Double bestBid;

    @JsonView({View.Admin.class, View.Gallery.class, View.Trader.class})
    private Double lastPrice;

}
