package io.malevich.server.rest.resources;

import io.malevich.server.domain.TradeTypeEntity;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.transfer.TradeTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/tradetype")
public class TradeTypeResource {

    @Autowired
    private TradeTypeService tradeTypeService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TradeTypeDto> list() {
        List<TradeTypeEntity> allEntries = this.tradeTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private TradeTypeDto convertToDto(TradeTypeEntity entity) {
        return modelMapper.map(entity, TradeTypeDto.class);
    }

    private TradeTypeEntity convertToEntity(TradeTypeDto dto) {
        return modelMapper.map(dto, TradeTypeEntity.class);
    }

}
