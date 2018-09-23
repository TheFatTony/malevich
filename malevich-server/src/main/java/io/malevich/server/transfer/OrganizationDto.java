package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class OrganizationDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String legalName;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private Map<String, String> legalNameMl;

    @Getter
    @Setter
    private Map<String, String> locationMl;

}
