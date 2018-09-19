package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "files")
public class FileEntity implements Entity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "file_name")

    @Getter @Setter
    private String fileName;

    @Column(name = "mime_type")
    @Getter @Setter
    private String mimeType;

    @Column(name = "url")
    @Getter @Setter
    private String url;

    @Column(name = "alt")
    @Getter @Setter
    private String alt;

    @Column(name = "file_size")
    @Getter @Setter
    private Long fileSize;


}
