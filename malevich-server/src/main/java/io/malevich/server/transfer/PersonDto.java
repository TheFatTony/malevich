package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PersonDto {

    private Long id;

    private String fullName;

    private Map<String, String> fullNameMl;

}
