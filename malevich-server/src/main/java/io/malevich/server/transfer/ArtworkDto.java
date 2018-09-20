package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;


public class ArtworkDto {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private CategoryDto category;

    @Getter
    @Setter
    private FileDto thumbnail;

    @Getter
    @Setter
    private Map<String, String> titleMl;

    @Getter
    @Setter
    private Map<String, String> descriptionMl;

}
