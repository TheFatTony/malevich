package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.CountryEntity;
import io.malevich.server.services.country.CountryService;
import io.malevich.server.transfer.CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/countries")
public class CountryResource {

    @Autowired
    private CountryService countryService;

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(CountryDto.class)
    public List<CountryEntity> list() {
        List<CountryEntity> allEntries = this.countryService.findAll();
        return allEntries;
    }

}
