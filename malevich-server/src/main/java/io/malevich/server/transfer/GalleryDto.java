package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GalleryDto {

    private Long id;

    private OrganizationDto organization;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> descriptionMl;

    private List<AddressDto> addresses;

}
