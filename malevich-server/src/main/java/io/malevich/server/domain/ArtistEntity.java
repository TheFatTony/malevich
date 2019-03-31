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
import java.sql.Timestamp;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "artist")
public class ArtistEntity extends YAbstractPersistable<Long> {

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
    @NotNull
    private Map<String, String> fullNameMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;

    @Getter
    @Setter
    private Timestamp dateOfBirth;

    @Getter
    @Setter
    private Timestamp dateOfDeath;

}
