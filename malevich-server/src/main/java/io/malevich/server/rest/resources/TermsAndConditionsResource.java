package io.malevich.server.rest.resources;

import com.yinyang.core.server.domain.UserTypeEntity;
import com.yinyang.core.server.transfer.UserTypeDto;
import io.malevich.server.services.termsandconditions.TermsAndConditionsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/termsAndConditions")
public class TermsAndConditionsResource {

    @Autowired
    private TermsAndConditionsService termsAndConditionsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public String getHtml(@RequestBody UserTypeDto userType) {
        UserTypeEntity userTypeEntity = modelMapper.map(userType, UserTypeEntity.class);
        String result = termsAndConditionsService.getHtmlByUserType(userTypeEntity);
        return result;
    }

}
