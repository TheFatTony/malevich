package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.transfer.PaymentMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/payment_methods_bitcoin")
public class PaymentMethodBitcoinResource extends RestResource<PaymentMethodDto, PaymentMethodBitcoinEntity> {

    @Autowired
    private PaymentMethodService paymentMethodService;

    public PaymentMethodBitcoinResource() {
        super(PaymentMethodDto.class, PaymentMethodBitcoinEntity.class);
    }


//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public List<PaymentMethodDto> list() {
//        List<PaymentMethodEntity> allEntries = this.paymentMethodService.findAll();
//        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
//    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PaymentMethodEntity generate() {
        return paymentMethodService.generateBtc();
    }

}
