package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "lob_storage")
public class LobStorageEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToOne
    private FileEntity file;

    @Getter
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private byte[] content;

}
