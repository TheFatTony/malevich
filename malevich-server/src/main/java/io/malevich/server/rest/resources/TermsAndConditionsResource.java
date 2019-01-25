package io.malevich.server.rest.resources;

import io.malevich.server.services.termsandconditions.TermsAndConditionsService;
import io.malevich.server.transfer.TermsAndConditionsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/termsAndConditions")
public class TermsAndConditionsResource {

    @Autowired
    private TermsAndConditionsService termsAndConditionsService;


    @RequestMapping(value = "/{lang}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TermsAndConditionsDto> getHtml(@PathVariable String lang) {
        List<TermsAndConditionsDto> result = termsAndConditionsService.getByLang(lang);
        return result;
    }

}
