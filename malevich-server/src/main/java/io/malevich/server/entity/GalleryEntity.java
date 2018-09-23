package io.malevich.server.entity;

import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

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
    @OneToOne
    private OrganizationEntity organization;


    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @ManyToOne
    private FileEntity thumbnail;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description_ml")
    private Map<String, String> descriptionMl;


}
