package io.malevich.server.rest.resources;

import io.malevich.server.entity.OrderEntity;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderDto> list() {
        List<OrderEntity> allEntries = this.orderService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getPlacedOrders", method = RequestMethod.GET)
    public List<OrderDto> getPlacedOrders() {
        List<OrderEntity> allEntries = this.orderService.getPlacedOrders();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/placeAsk", method = RequestMethod.POST)
    public ResponseEntity<Void> placeAsk(@RequestBody OrderDto trader) {
        OrderEntity newTraderEntity = convertToEntity(trader);
        this.orderService.placeAsk(newTraderEntity);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST)
    public ResponseEntity<Void> placeBid(@RequestBody OrderDto trader) {
        OrderEntity newTraderEntity = convertToEntity(trader);
        this.orderService.placeBid(newTraderEntity);
        return ResponseEntity.ok().build();
    }

    private OrderDto convertToDto(OrderEntity entity) {
        return modelMapper.map(entity, OrderDto.class);
    }

    private OrderEntity convertToEntity(OrderDto dto) {
        return modelMapper.map(dto, OrderEntity.class);
    }

}
