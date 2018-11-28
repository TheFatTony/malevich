package io.malevich.server.rest.resources;

import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.transfer.DelayedChangeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/delayedChanges")
public class DelayedChangeResource {

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DelayedChangeDto> list() {
        List<DelayedChangeEntity> allEntries = this.delayedChangeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private DelayedChangeDto convertToDto(DelayedChangeEntity files) {
        DelayedChangeDto filesDto = modelMapper.map(files, DelayedChangeDto.class);
        return filesDto;
    }

    private DelayedChangeEntity convertToEntity(DelayedChangeDto filesDto) {
        DelayedChangeEntity files = modelMapper.map(filesDto, DelayedChangeEntity.class);
        return files;
    }

}
