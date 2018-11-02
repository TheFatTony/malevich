package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "files")
public class FileEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "file_name")
    private String fileName;

    @Getter
    @Setter
    @Column(name = "mime_type")
    private String mimeType;

    @Getter
    @Setter
    @Column(name = "url")
    private String url;

    @Getter
    @Setter
    @Column(name = "alt")
    private String alt;

    @Getter
    @Setter
    @Column(name = "file_size")
    private Long fileSize;


}
