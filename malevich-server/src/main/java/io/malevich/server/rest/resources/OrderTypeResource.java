package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.OrderTypeEntity;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.transfer.OrderTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/ordertype")
public class OrderTypeResource {

    @Autowired
    private OrderTypeService orderTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(OrderTypeDto.class)
    public List<OrderTypeEntity> list() {
        List<OrderTypeEntity> allEntries = this.orderTypeService.findAll();
        return allEntries;
    }

}
