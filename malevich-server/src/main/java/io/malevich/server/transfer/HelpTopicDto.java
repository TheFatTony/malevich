package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HelpTopicDto {

    private Long id;

    private Map<String, String> bodyMl;

    private Map<String, String> topicNameMl;

    private HelpCategoryDto helpCategory;
}
