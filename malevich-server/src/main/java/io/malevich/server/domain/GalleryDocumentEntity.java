package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "gallery_document")
public class GalleryDocumentEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    private DocumentEntity document;

    @Getter
    @Setter
    @ManyToOne
    private GalleryEntity gallery;

}
