package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ArtistDto {


    private Long id;

    private PersonDto person;

    private String description;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> descriptionMl;

}
