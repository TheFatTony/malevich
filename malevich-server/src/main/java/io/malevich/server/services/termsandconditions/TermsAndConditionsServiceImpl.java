package io.malevich.server.services.termsandconditions;


import io.malevich.server.domain.MailQueueEntity;
import io.malevich.server.transfer.TermsAndConditionsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Locale;


@Slf4j
@Service
public class TermsAndConditionsServiceImpl implements TermsAndConditionsService {

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public TermsAndConditionsDto getByLang(String lang) {
        VelocityContext context = new VelocityContext();
        StringWriter stringWriter = new StringWriter();

        velocityEngine.mergeTemplate(
                "templates/pages/terms_and_conditions_" + lang + ".vm",
                "UTF-8",
                context,
                stringWriter);

        TermsAndConditionsDto result = new TermsAndConditionsDto();
        result.setHtmlText(stringWriter.toString());

        return result;
    }
}
