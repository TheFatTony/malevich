package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.transfer.PaymentMethodDto;
import io.malevich.server.transfer.PaymentMethodSuperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/payment_methods")
public class PaymentMethodResource extends RestResource<PaymentMethodSuperDto, PaymentMethodEntity> {

    @Autowired
    private PaymentMethodService paymentMethodService;

    public PaymentMethodResource() {
        super(PaymentMethodSuperDto.class, PaymentMethodEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodSuperDto> list() {
        List<PaymentMethodEntity> allEntries = this.paymentMethodService.findAll();
        return convertListOfDto(allEntries);
    }

}
