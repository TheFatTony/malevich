package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.OrderStatusEntity;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.transfer.OrderStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/order_status")
public class OrderStatusResource extends RestResource<OrderStatusDto, OrderStatusEntity> {

    @Autowired
    private OrderStatusService orderStatusService;

    public OrderStatusResource() {
        super(OrderStatusDto.class, OrderStatusEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderStatusDto> list() {
        List<OrderStatusEntity> allEntries = this.orderStatusService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


}
