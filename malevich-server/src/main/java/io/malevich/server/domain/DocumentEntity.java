package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "document")
public class DocumentEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "document_name")
    private String fileName;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private Timestamp effectiveDate;

    @Getter
    @Setter
    @ManyToOne
    private DocumentTypeEntity documentType;

    @Getter
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private byte[] content;

}
