package io.malevich.server.rest.resources;

import io.malevich.server.domain.CountryEntity;
import io.malevich.server.services.country.CountryService;
import io.malevich.server.transfer.CountryDto;
import io.malevich.server.transfer.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/countries")
public class CountryResource {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CountryDto> list() {
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
