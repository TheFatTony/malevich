package io.malevich.server.services.termsandconditions;


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
import java.util.List;
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

    private String getText(String template) {
        VelocityContext context = new VelocityContext();
        StringWriter stringWriter = new StringWriter();

        velocityEngine.mergeTemplate(
                template,
                "UTF-8",
                context,
                stringWriter);

        return stringWriter.toString();
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
}
