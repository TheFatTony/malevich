package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class PersonDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private Map<String, String> fullNameMl;

}
