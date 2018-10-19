package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OrganizationDto {

    private Long id;

    private String legalName;

    private String phoneNumber;

    private Map<String, String> legalNameMl;

}
