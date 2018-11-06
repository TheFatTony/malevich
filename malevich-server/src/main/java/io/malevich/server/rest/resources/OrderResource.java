package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(OrderDto.class)
    public List<OrderEntity> list() {
        List<OrderEntity> allEntries = this.orderService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/getPlacedOrders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderEntity> getPlacedOrders() {
        List<OrderEntity> allEntries = this.orderService.getPlacedOrders();
        return allEntries;
    }

    @RequestMapping(value = "/getOrdersByArtworkId/{artworkId}", method = RequestMethod.GET)
    public List<OrderEntity> getOrdersByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<OrderEntity> allEntries = this.orderService.getOrdersByArtworkStockId(artworkId);
        return allEntries;
    }

    @PreAuthorize("hasAnyRole('GALLERY', 'TRADER')")
    @RequestMapping(value = "/placeAsk", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> placeAsk(@DTO(OrderDto.class) @RequestBody OrderEntity trader) {
        this.orderService.placeAsk(trader);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> placeBid(@DTO(OrderDto.class) @RequestBody OrderEntity trader) {
        this.orderService.placeBid(trader);
        return ResponseEntity.ok().build();
    }


}
