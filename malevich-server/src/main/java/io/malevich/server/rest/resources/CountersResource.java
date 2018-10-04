package io.malevich.server.rest.resources;

import io.malevich.server.entity.ArtistEntity;
import io.malevich.server.entity.InvolvementEntity;
import io.malevich.server.services.artist.ArtistService;
import io.malevich.server.services.counters.CountersService;
import io.malevich.server.transfer.ArtistDto;
import io.malevich.server.transfer.InvolvementDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/counters")
public class CountersResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountersService countersService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/involvementCounters", method = RequestMethod.GET)
    public InvolvementDto getInvolvementCounters() {
        this.logger.info("getInvolvementCounters()");
        InvolvementEntity allEntry = this.countersService.getInvolvementCounters();
        return convertToDto(allEntry);
    }

    private InvolvementDto convertToDto(InvolvementEntity files) {
        InvolvementDto filesDto = modelMapper.map(files, InvolvementDto.class);
        return filesDto;
    }

    private InvolvementEntity convertToEntity(InvolvementDto filesDto) {
        InvolvementEntity files = modelMapper.map(filesDto, InvolvementEntity.class);
        return files;
    }

}
