package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @Column(name = "effective_date")
    private Timestamp effectiveDate;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.DETACH)
    private DocumentTypeEntity documentType;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    private ParticipantEntity participant;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private FileEntity files;

}
