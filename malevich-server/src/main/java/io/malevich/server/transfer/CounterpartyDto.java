package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CounterpartyDto {

    private Long id;

    private String typeId;

    private UserDto user;

    private Boolean isOrganization;

    private Boolean isGallery;

    private PersonDto person;

    private OrganizationDto organization;

    private GalleryDto gallery;

    private FileDto image;
}
