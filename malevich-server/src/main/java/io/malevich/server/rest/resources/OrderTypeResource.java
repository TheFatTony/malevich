package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.OrderTypeEntity;
import io.malevich.server.services.ordertype.OrderTypeService;
import io.malevich.server.transfer.OrderTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/ordertype")
public class OrderTypeResource extends RestResource<OrderTypeDto, OrderTypeEntity> {

    @Autowired
    private OrderTypeService orderTypeService;

    public OrderTypeResource() {
        super(OrderTypeDto.class, OrderTypeEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderTypeDto> list() {
        List<OrderTypeEntity> allEntries = this.orderTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
