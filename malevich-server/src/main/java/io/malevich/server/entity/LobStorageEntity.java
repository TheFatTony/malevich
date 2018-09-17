package io.malevich.server.entity;


import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "lob_storage")
public class LobStorageEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private Blob content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public FileEntity getFile() {
        return file;
    }


    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }


}
