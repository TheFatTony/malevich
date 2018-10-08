package io.malevich.server.rest.resources;

import io.malevich.server.entity.GenderEntity;
import io.malevich.server.services.gender.GenderService;
import io.malevich.server.transfer.FileDto;
import io.malevich.server.transfer.GenderDto;
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
@RequestMapping(value = "/genders")
public class GenderResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GenderService genderService;

    @Autowired
    private ModelMapper modelMapper;
    
    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<GenderDto> list() {
        this.logger.info("list()");
        List<GenderEntity> allEntries = this.genderService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private GenderDto convertToDto(GenderEntity files) {
        GenderDto filesDto = modelMapper.map(files, GenderDto.class);
        return filesDto;
    }

    private GenderEntity convertToEntity(FileDto filesDto) {
        GenderEntity files = modelMapper.map(filesDto, GenderEntity.class);
        return files;
    }

}
