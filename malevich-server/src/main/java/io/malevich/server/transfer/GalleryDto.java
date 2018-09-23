package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class GalleryDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private OrganizationDto organization;


    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private FileDto thumbnail;

    @Getter
    @Setter
    private Map<String, String> descriptionMl;


}
