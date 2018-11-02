package io.malevich.server.domain;

import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "artist")
public class ArtistEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne
    private PersonEntity person;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "full_name_ml")
    private Map<String, String> fullNameMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

}
