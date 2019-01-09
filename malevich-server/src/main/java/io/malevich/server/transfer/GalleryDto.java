package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GalleryDto extends ParticipantDto {

    private OrganizationDto organization;

    private FileDto image;

    private Map<String, String> descriptionMl;
}
