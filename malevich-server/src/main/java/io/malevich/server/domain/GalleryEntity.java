package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "gallery")
public class GalleryEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
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
