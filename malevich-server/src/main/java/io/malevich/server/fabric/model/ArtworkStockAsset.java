package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkStockAsset {

    private String id;

    private String token;

    private Double currentAsk;

    private Double lastPrice;

    private Integer dealCount;

    private Boolean confirmed;

    private String owner;

    private String holder;

}
