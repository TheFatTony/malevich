package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "gallery")
@PrimaryKeyJoinColumn(name = "participant_id")
@DiscriminatorValue("G")
public class GalleryEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private OrganizationEntity organization;


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private FileEntity image;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;


}
