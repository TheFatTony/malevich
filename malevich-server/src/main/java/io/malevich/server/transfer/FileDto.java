package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

public class FileDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String fileName;

    @Getter
    @Setter
    private String mimeType;

    @Getter
    @Setter
    private Long fileSize;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String alt;

}
