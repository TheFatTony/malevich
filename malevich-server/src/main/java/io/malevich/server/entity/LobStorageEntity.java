package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@javax.persistence.Entity
@Table(name = "lob_storage")
public class LobStorageEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @Getter @Setter
    private FileEntity file;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    @Getter @Setter
    private Blob content;

}
