package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.aop.KycRequired;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.services.order.OrderService;
import io.malevich.server.transfer.OrderDto;
import io.malevich.server.transfer.OrderPublicDto;
import lombok.extern.slf4j.Slf4j;
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
public class OrderResource extends RestResource<OrderDto, OrderEntity> {

    @Autowired
    private OrderService orderService;

    public OrderResource() {
        super(OrderDto.class, OrderEntity.class);
    }

    @KycRequired(level = {KycLevel.G_TIER1, KycLevel.T_TIER1})
    @RequestMapping(value = "/getPlacedOrders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDto> getPlacedOrders() {
        List<OrderEntity> allEntries = this.orderService.getPlacedOrders();
        return convertListOfDto(allEntries);
    }

    @KycRequired(level = {KycLevel.G_TIER0, KycLevel.T_TIER0})
    @RequestMapping(value = "/getOpenOrdersByArtworkId/{artworkId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderPublicDto> getOpenOrdersByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<OrderEntity> allEntries = this.orderService.getOpenOrdersByArtworkStockId(artworkId);
        return allEntries.stream().map(allEntry -> convertToPublicDto(allEntry)).collect(Collectors.toList());
    }

    @KycRequired(level = {KycLevel.G_TIER1, KycLevel.T_TIER1})
    @PreAuthorize("hasAnyRole('ROLE_GALLERY', 'ROLE_TRADER')")
    @RequestMapping(value = "/placeAsk", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> placeAsk(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = convertToEntity(orderDto);
        this.orderService.placeAsk(orderEntity);
        return ResponseEntity.ok().build();
    }

    @KycRequired(level = KycLevel.T_TIER1)
    @PreAuthorize("hasRole('ROLE_TRADER')")
    @RequestMapping(value = "/placeBid", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> placeBid(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = convertToEntity(orderDto);
        this.orderService.placeBid(orderEntity);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ROLE_GALLERY', 'ROLE_TRADER')")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> cancel(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = convertToEntity(orderDto);
        this.orderService.cancelOwnOrder(orderEntity);
        return ResponseEntity.ok().build();
    }

    private OrderPublicDto convertToPublicDto(OrderEntity entity) {
        return modelMapper.map(entity, OrderPublicDto.class);
    }


}
