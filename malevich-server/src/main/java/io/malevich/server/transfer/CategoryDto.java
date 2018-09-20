package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;


public class CategoryDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String categoryName;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Map<String, String> categoryNameMl;


}
