package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "wish_list")
public class WishListEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @ManyToOne
    private TraderEntity trader;

}
