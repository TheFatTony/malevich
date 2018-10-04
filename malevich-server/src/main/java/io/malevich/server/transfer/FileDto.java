package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {

    private Long id;

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private String url;

    private String alt;

}
