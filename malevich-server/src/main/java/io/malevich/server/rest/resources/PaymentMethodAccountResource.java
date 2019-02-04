package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.paymentmethodaccount.PaymentMethodAccountService;
import io.malevich.server.transfer.PaymentMethodAccountDto;
import io.malevich.server.transfer.PaymentMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/payment_methods_account")
public class PaymentMethodAccountResource extends RestResource<PaymentMethodAccountDto, PaymentMethodAccountEntity> {

    @Autowired
    private PaymentMethodAccountService paymentMethodAccountService;

    public PaymentMethodAccountResource() {
        super(PaymentMethodAccountDto.class, PaymentMethodAccountEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PaymentMethodAccountDto> list() {
        List<PaymentMethodAccountEntity> allEntries = this.paymentMethodAccountService.findAll();
        return convertListOfDto(allEntries);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> save(@RequestBody PaymentMethodAccountDto dto) {
        PaymentMethodAccountEntity entity = convertToEntity(dto);
        paymentMethodAccountService.save(entity);
        return ResponseEntity.ok().build();
    }
}
