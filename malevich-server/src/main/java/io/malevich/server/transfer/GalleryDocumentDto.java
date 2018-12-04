package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GalleryDocumentDto {

    private Long id;

    private DocumentDto document;

    private GalleryDto gallery;

}
