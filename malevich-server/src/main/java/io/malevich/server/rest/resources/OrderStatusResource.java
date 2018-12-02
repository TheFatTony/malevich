package io.malevich.server.rest.resources;

import io.malevich.server.domain.OrderStatusEntity;
import io.malevich.server.services.orderstatus.OrderStatusService;
import io.malevich.server.transfer.OrderStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/order_status")
public class OrderStatusResource {

  @Autowired
  private OrderStatusService orderStatusService;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<OrderStatusDto> list() {
  List<OrderStatusEntity> allEntries = this.orderStatusService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private OrderStatusDto convertToDto(OrderStatusEntity entity) {
    return modelMapper.map(entity, OrderStatusDto.class);
  }

  private OrderStatusEntity convertToEntity(OrderStatusDto dto) {
    return modelMapper.map(dto, OrderStatusEntity.class);
  }

}
