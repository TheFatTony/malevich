package io.malevich.server.transfer;

import com.yinyang.core.server.transfer.FileDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ArtistDto {


    private Long id;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> fullNameMl;

    private Map<String, String> descriptionMl;

}
