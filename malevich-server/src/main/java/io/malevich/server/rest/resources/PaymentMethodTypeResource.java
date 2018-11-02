package io.malevich.server.rest.resources;

import io.malevich.server.domain.PaymentMethodTypeEntity;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import io.malevich.server.transfer.PaymentMethodTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/payment_method_types")
public class PaymentMethodTypeResource {

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodTypeDto> list() {
        List<PaymentMethodTypeEntity> allEntries = this.paymentMethodTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private PaymentMethodTypeDto convertToDto(PaymentMethodTypeEntity entity) {
        return modelMapper.map(entity, PaymentMethodTypeDto.class);
    }

    private PaymentMethodTypeEntity convertToEntity(PaymentMethodTypeDto dto) {
        return modelMapper.map(dto, PaymentMethodTypeEntity.class);
    }

}
