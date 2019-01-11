package io.malevich.server.domain;


import com.yinyang.core.server.domain.FileEntity;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;


@javax.persistence.Entity
@Table(name = "document")
public class DocumentEntity extends YAbstractPersistable<Long> {


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
