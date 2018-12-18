package io.malevich.server.rest.resources;


import io.malevich.server.domain.TraderEntity;
import io.malevich.server.transfer.TraderDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/traders")
public class TraderResource {

    @Autowired
    private ModelMapper modelMapper;

    private TraderDto convertToDto(TraderEntity entity) {
        TraderDto dto = modelMapper.map(entity, TraderDto.class);
        return dto;
    }

    private TraderEntity convertToEntity(TraderDto filesDto) {
        TraderEntity files = modelMapper.map(filesDto, TraderEntity.class);
        return files;
    }

}
