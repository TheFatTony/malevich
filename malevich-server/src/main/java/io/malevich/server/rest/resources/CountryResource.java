package io.malevich.server.rest.resources;

import io.malevich.server.domain.CountryEntity;
import io.malevich.server.services.country.CountryService;
import io.malevich.server.transfer.CountryDto;
import io.malevich.server.transfer.FileDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/countries")
public class CountryResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryService countryService;

    @Autowired
    private ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CountryDto> list() {
        this.logger.info("list()");
        List<CountryEntity> allEntries = this.countryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private CountryDto convertToDto(CountryEntity files) {
        CountryDto filesDto = modelMapper.map(files, CountryDto.class);
        return filesDto;
    }

    private CountryEntity convertToEntity(FileDto filesDto) {
        CountryEntity files = modelMapper.map(filesDto, CountryEntity.class);
        return files;
    }

}
