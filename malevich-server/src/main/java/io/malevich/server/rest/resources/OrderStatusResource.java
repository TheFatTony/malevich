package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.OrderStatusEntity;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.transfer.OrderStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/order_status")
public class OrderStatusResource {

  @Autowired
  private OrderStatusService orderStatusService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @DTO(OrderStatusDto.class)
  public List<OrderStatusEntity> list() {
  List<OrderStatusEntity> allEntries = this.orderStatusService.findAll();
    return allEntries;
  }

}
