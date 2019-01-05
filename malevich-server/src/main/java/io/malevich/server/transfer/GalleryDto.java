package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GalleryDto {

    private Long id;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> titleMl;

    private Map<String, String> descriptionMl;
}
