package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

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
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file;

    @Getter
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private Blob content;

}
