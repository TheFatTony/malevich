package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import com.yinyang.core.server.domain.Entity;

@EqualsAndHashCode
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
    @NotNull
    private ArtworkEntity artwork;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    @JoinColumn(name = "gallery_id")
    @NotNull
    private GalleryEntity gallery;

    @Getter
    @Setter
    @Column(name = "price")
    private double price;

}
