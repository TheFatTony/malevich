package io.malevich.server.transfer;

import com.fasterxml.jackson.annotation.JsonView;
import io.malevich.server.core.dto.View;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderPublicDto {

    @JsonView({View.Admin.class, View.Gallery.class, View.Trader.class})
    private Long id;

    private OrderTypeDto type;

    private ArtworkStockDto artworkStock;

    private Double amount;

    private Boolean isOwn;

}
