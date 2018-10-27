package io.malevich.server.entity;


import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "artwork")
public class ArtworkEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "estimated_price")
    private double estimatedPrice;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private CategoryEntity category;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private ArtistEntity artist;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "title_ml")
    private Map<String, String> titleMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

}
