package io.malevich.server.rest.resources;

import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.transfer.CounterpartyDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/counterparty")
public class CounterpartyResource {

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CounterpartyDto> list() {
        List<CounterpartyEntity> allEntries = this.counterpartyService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private CounterpartyDto convertToDto(CounterpartyEntity entity) {
        return modelMapper.map(entity, CounterpartyDto.class);
    }

    private CounterpartyEntity convertToEntity(CounterpartyDto dto) {
        return modelMapper.map(dto, CounterpartyEntity.class);
    }

}
