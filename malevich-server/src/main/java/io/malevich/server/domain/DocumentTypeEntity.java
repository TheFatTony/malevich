package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "document_type")
public class DocumentTypeEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "document_type_name")
    private String typeName;

    @Getter
    @Setter
    @Column(name = "user_type")
    private String userType;

}
