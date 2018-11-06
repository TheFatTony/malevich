package io.malevich.server.rest.resources;

import io.malevich.server.domain.InvolvementEntity;
import io.malevich.server.services.counters.CountersService;
import io.malevich.server.transfer.InvolvementDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
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
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
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
