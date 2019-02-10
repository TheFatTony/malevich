package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ExchangeOrderEntity;
import io.malevich.server.services.exchangeorder.ExchangeOrderService;
import io.malevich.server.transfer.ExchangeOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/exchange_order")
public class ExchangeOrderResource extends RestResource<ExchangeOrderDto, ExchangeOrderEntity> {

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    public ExchangeOrderResource() {
        super(ExchangeOrderDto.class, ExchangeOrderEntity.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ExchangeOrderDto> list() {
        List<ExchangeOrderEntity> allEntries = this.exchangeOrderService.findAll();
        return convertListOfDto(allEntries);
    }

}
