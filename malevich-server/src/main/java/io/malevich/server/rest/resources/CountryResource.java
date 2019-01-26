package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.CountryEntity;
import io.malevich.server.services.country.CountryService;
import io.malevich.server.transfer.CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/countries")
public class CountryResource extends RestResource<CountryDto, CountryEntity> {

    @Autowired
    private CountryService countryService;

    public CountryResource() {
        super(CountryDto.class, CountryEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CountryDto> list() {
        List<CountryEntity> allEntries = this.countryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
