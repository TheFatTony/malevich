package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.transfer.PaymentMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/payment_methods_bitcoin")
public class PaymentMethodBitcoinResource extends RestResource<PaymentMethodDto, PaymentMethodBitcoinEntity> {

    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    public PaymentMethodBitcoinResource() {
        super(PaymentMethodDto.class, PaymentMethodBitcoinEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodDto> list() {
        List<PaymentMethodBitcoinEntity> allEntries = this.paymentMethodBitcoinService.findAll();
        return convertListOfDto(allEntries);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PaymentMethodBitcoinEntity generate() {
        return paymentMethodBitcoinService.generateBtc();
    }

}
