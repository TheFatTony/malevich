package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Getter
@Setter
@XmlRootElement(name = "artwork")
public class ArtworkDto {

    private long id;

    private String artwork;

    private String description;

    private double price;

    private CategoryDto category;

    private FileDto thumbnail;

    private FileDto image;

    private Map<String, String> titleMl;

    private Map<String, String> descriptionMl;

}
