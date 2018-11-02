package io.malevich.server.rest.resources;

import io.malevich.server.domain.OrderTypeEntity;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.transfer.OrderTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/ordertype")
public class OrderTypeResource {

    @Autowired
    private OrderTypeService orderTypeService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderTypeDto> list() {
        List<OrderTypeEntity> allEntries = this.orderTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private OrderTypeDto convertToDto(OrderTypeEntity entity) {
        return modelMapper.map(entity, OrderTypeDto.class);
    }

    private OrderTypeEntity convertToEntity(OrderTypeDto dto) {
        return modelMapper.map(dto, OrderTypeEntity.class);
    }

}
