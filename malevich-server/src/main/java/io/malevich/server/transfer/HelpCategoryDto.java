package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HelpCategoryDto {

    private Long id;

    private Map<String, String> categoryNameMl;

}
