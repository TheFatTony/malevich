package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.PaymentMethodTypeEntity;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import io.malevich.server.transfer.PaymentMethodTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/payment_method_types")
public class PaymentMethodTypeResource {

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(PaymentMethodTypeDto.class)
    public List<PaymentMethodTypeEntity> list() {
        List<PaymentMethodTypeEntity> allEntries = this.paymentMethodTypeService.findAll();
        return allEntries;
    }

}
