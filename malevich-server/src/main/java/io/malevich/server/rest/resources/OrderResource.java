package io.malevich.server.rest.resources;

import io.malevich.server.entity.OrderEntity;
import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import io.malevich.server.transfer.TraderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/order")
public class OrderResource {

  @Autowired
  private OrderService orderService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/getPlacedOrders", method = RequestMethod.GET)
  public List<OrderDto> list() {
  List<OrderEntity> allEntries = this.orderService.getPlacedOrders();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody OrderDto trader) {
        OrderEntity newTraderEntity = convertToEntity(trader);
        this.orderService.insert(newTraderEntity);
        return ResponseEntity.ok().build();
    }

  private OrderDto convertToDto(OrderEntity entity) {
    return modelMapper.map(entity, OrderDto.class);
  }

  private OrderEntity convertToEntity(OrderDto dto) {
    return modelMapper.map(dto, OrderEntity.class);
  }

}
