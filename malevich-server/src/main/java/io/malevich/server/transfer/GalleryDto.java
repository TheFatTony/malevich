package io.malevich.server.transfer;

import com.yinyang.core.server.transfer.FileDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GalleryDto extends ParticipantDto {

    private OrganizationDto organization;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> titleMl;

    private Map<String, String> descriptionMl;
}
