package io.malevich.server.rest.resources;

import io.malevich.server.entity.OrderEntity;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class OrderResource {

  @Autowired
  private OrderService orderService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<OrderDto> list() {
  List<OrderEntity> allEntries = this.orderService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private OrderDto convertToDto(OrderEntity entity) {
    return modelMapper.map(entity, OrderDto.class);
  }

  private OrderEntity convertToEntity(OrderDto dto) {
    return modelMapper.map(dto, OrderEntity.class);
  }

}
