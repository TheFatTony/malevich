package io.malevich.server.services.termsandconditions;


import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yinyang.core.server.domain.UserTypeEntity;
import com.yinyang.core.server.services.usertype.UserTypeService;
import com.yinyang.core.server.transfer.UserTypeDto;
import io.malevich.server.transfer.TermsAndConditionsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TermsAndConditionsServiceImpl implements TermsAndConditionsService {

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private ModelMapper modelMapper;

    private String getText(String template, Map<String, Object> context) {
        VelocityContext velocityContext = new VelocityContext(context);
        StringWriter stringWriter = new StringWriter();

        velocityEngine.mergeTemplate(
                template,
                "UTF-8",
                velocityContext,
                stringWriter);

        return stringWriter.toString();
    }

    private String getText(String template) {
        return getText(template, Collections.emptyMap());
    }

    @Override
    public List<TermsAndConditionsDto> getByLang(String lang) {

        return userTypeService.findAll()
                .stream()
                .map(type -> {
                    TermsAndConditionsDto result = new TermsAndConditionsDto();
                    UserTypeDto typeDto = modelMapper.map(type, UserTypeDto.class);
                    String text =
                            getText("templates/pages/terms_and_conditions_" +
                                    type.getTypeName() + "_" + lang + ".vm");
                    result.setUserType(typeDto);
                    result.setHtmlText(text);
                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getHtmlByUserType(UserTypeEntity userTypeEntity) {
        HashMap<String, Object> context = new HashMap<String, Object>(){
            {
                put("userType", userTypeEntity.getTypeName());
            }
        };

        String text =
                getText("templates/pages/terms_and_conditions.vm", context);

        return text;
    }
}
