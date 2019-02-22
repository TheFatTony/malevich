package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodDepositReferenceEntity;
import io.malevich.server.services.paymentmethoddepositreference.PaymentMethodDepositReferenceService;
import io.malevich.server.transfer.PaymentMethodDepositReferenceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/payment_methods_deposit_reference")
public class PaymentMethodDepositReference extends RestResource<PaymentMethodDepositReferenceDto, PaymentMethodDepositReferenceEntity> {

    @Autowired
    private PaymentMethodDepositReferenceService paymentMethodDepositReferenceService;

    public PaymentMethodDepositReference() {
        super(PaymentMethodDepositReferenceDto.class, PaymentMethodDepositReferenceEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodDepositReferenceDto> list() {
        List<PaymentMethodDepositReferenceEntity> allEntries = this.paymentMethodDepositReferenceService.findAll();
        return convertListOfDto(allEntries);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PaymentMethodDepositReferenceDto getOrCreate() {
        PaymentMethodDepositReferenceEntity entity = paymentMethodDepositReferenceService.getOrCreate();
        return convertToDto(entity);
    }

}
