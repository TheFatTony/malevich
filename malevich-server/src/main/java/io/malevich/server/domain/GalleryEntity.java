package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.FileEntity;
import io.malevich.server.aop.KycRequiredFor;
import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "gallery")
public class GalleryEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    @KycRequiredFor(levels = KycLevel.G_TIER1)
    private OrganizationEntity organization;


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private FileEntity image;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private FileEntity thumbnail;

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
