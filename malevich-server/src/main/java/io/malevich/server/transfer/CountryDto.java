package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CountryDto {
    private String id;
    private String name;
    private Map<String, String> nameMl;
}
