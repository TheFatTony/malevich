package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CategoryDto {

    private Long id;

    private String categoryName;

    private String description;

    private Map<String, String> categoryNameMl;


}
