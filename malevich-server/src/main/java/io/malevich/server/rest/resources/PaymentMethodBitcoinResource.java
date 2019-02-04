package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.transfer.PaymentMethodBitcoinDto;
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
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/payment_methods_bitcoin")
public class PaymentMethodBitcoinResource extends RestResource<PaymentMethodBitcoinDto, PaymentMethodBitcoinEntity> {

    @Autowired
    private PaymentMethodBitcoinService paymentMethodBitcoinService;

    public PaymentMethodBitcoinResource() {
        super(PaymentMethodBitcoinDto.class, PaymentMethodBitcoinEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodBitcoinDto> list() {
        List<PaymentMethodBitcoinEntity> allEntries = this.paymentMethodBitcoinService.findAll();
        return convertListOfDto(allEntries);
    }


    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PaymentMethodBitcoinDto generate() {
        PaymentMethodBitcoinEntity entity = paymentMethodBitcoinService.generateBtc();
        return convertToDto(entity);
    }

}
