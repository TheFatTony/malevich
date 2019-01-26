package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodTypeEntity;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import io.malevich.server.transfer.PaymentMethodTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/payment_method_types")
public class PaymentMethodTypeResource extends RestResource<PaymentMethodTypeDto, PaymentMethodTypeEntity> {

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    public PaymentMethodTypeResource() {
        super(PaymentMethodTypeDto.class, PaymentMethodTypeEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodTypeDto> list() {
        List<PaymentMethodTypeEntity> allEntries = this.paymentMethodTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


}
