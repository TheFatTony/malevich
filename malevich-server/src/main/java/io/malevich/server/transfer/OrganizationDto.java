package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrganizationDto {

    private Long id;

    private String phoneNumber;

    private Map<String, String> legalNameMl;

    private List<AddressDto> addresses;

}
