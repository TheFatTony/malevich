package io.malevich.server.transfer;


import com.yinyang.core.server.transfer.FileDto;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Getter
@Setter
@XmlRootElement(name = "artwork")
public class ArtworkDto {

    private Long id;

    private Double estimatedPrice;

    private CategoryDto category;

    private ArtistDto artist;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> titleMl;

    private Map<String, String> descriptionMl;

}
