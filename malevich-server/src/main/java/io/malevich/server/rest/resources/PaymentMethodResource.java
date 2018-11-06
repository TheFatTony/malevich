package io.malevich.server.rest.resources;

import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.transfer.PaymentMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/payment_methods")
public class PaymentMethodResource {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodDto> list() {
        List<PaymentMethodEntity> allEntries = this.paymentMethodService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private PaymentMethodDto convertToDto(PaymentMethodEntity entity) {
        return modelMapper.map(entity, PaymentMethodDto.class);
    }

    private PaymentMethodEntity convertToEntity(PaymentMethodDto dto) {
        return modelMapper.map(dto, PaymentMethodEntity.class);
    }

}
