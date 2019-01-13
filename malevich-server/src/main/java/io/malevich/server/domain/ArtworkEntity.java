package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.FileEntity;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "artwork")
public class ArtworkEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Column(name = "estimated_price")
    private double estimatedPrice;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private CategoryEntity category;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private ArtistEntity artist;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    @NotNull
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne()
    @NotNull
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "title_ml")
    @NotNull
    private Map<String, String> titleMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

}
