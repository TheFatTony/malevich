package io.malevich.server.transfer;

import com.yinyang.core.server.transfer.FileDto;
import com.yinyang.core.server.transfer.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParticipantDto {

    private Long id;

    private ParticipantTypeDto type;

    private String phoneNumber;

    private CountryDto country;

    private List<UserDto> users;

    private List<AddressDto> addresses;

    private FileDto thumbnail;

    private KycLevelDto kycLevel;

    //gallery
    private OrganizationDto organization;

    private FileDto image;

    private Map<String, String> titleMl;

    private Map<String, String> descriptionMl;

    //trader
    private PersonDto person;

}