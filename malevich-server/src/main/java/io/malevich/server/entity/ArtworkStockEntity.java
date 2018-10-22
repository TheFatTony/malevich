package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "artwork_stock")
public class ArtworkStockEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne()
    private ArtworkEntity artwork;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    private GalleryEntity gallery;

    @Getter
    @Setter
    @Column(name = "price")
    private double price;

}
