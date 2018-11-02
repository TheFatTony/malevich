package io.malevich.server.rest.resources;

import io.malevich.server.domain.OrderTypeEntity;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.transfer.OrderTypeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/ordertype")
public class OrderTypeResource {

    @Autowired
    private OrderTypeService service;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderTypeDto> list() {
        List<OrderTypeEntity> allEntries = this.service.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private OrderTypeDto convertToDto(OrderTypeEntity entity) {
        return modelMapper.map(entity, OrderTypeDto.class);
    }

    private OrderTypeEntity convertToEntity(OrderTypeDto dto) {
        return modelMapper.map(dto, OrderTypeEntity.class);
    }

}
