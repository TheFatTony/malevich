package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodaccount.PaymentMethodAccountService;
import io.malevich.server.transfer.PaymentMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/payment_methods_account")
public class PaymentMethodAccountResource extends RestResource<PaymentMethodDto, PaymentMethodAccountEntity> {

    @Autowired
    private PaymentMethodAccountService paymentMethodAccountService;

    public PaymentMethodAccountResource() {
        super(PaymentMethodDto.class, PaymentMethodAccountEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodDto> list() {
        List<PaymentMethodAccountEntity> allEntries = this.paymentMethodAccountService.findAll();
        return convertListOfDto(allEntries);
    }

}
