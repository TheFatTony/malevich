package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.FileEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "artist")
public class ArtistEntity extends AbstractPersistable<Long> {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "full_name_ml")
    @NotNull
    private Map<String, String> fullNameMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

}
