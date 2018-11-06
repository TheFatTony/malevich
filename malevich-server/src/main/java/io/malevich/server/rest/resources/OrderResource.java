package io.malevich.server.rest.resources;

import io.malevich.server.domain.OrderEntity;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDto> list() {
        List<OrderEntity> allEntries = this.orderService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getPlacedOrders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDto> getPlacedOrders() {
        List<OrderEntity> allEntries = this.orderService.getPlacedOrders();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getOrdersByArtworkId/{artworkId}", method = RequestMethod.GET)
    public List<OrderDto> getOrdersByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<OrderEntity> allEntries = this.orderService.getOrdersByArtworkStockId(artworkId);
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('GALLERY', 'TRADER')")
    @RequestMapping(value = "/placeAsk", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> placeAsk(@RequestBody OrderDto trader) {
        OrderEntity newTraderEntity = convertToEntity(trader);
        this.orderService.placeAsk(newTraderEntity);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
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
