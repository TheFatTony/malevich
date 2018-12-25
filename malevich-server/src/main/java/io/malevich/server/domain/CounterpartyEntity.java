package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "counterparty")
public class CounterpartyEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private CounterpartyTypeEntity type;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.DETACH)
    private UserEntity user;

    @Getter
    @Setter
    private Boolean isOrganization;

    @Getter
    @Setter
    private Boolean isGallery;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    private PersonEntity person;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    private OrganizationEntity organization;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    private GalleryEntity gallery;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity image;

}
