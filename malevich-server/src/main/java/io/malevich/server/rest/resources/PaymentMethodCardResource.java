package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.PaymentMethodCardEntity;
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
@RequestMapping(value = "/payment_methods_card")
public class PaymentMethodCardResource extends RestResource<PaymentMethodDto, PaymentMethodCardEntity> {

    @Autowired
    private PaymentMethodService paymentMethodService;

    public PaymentMethodCardResource() {
        super(PaymentMethodDto.class, PaymentMethodCardEntity.class);
    }

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public List<PaymentMethodDto> list() {
//        List<PaymentMethodEntity> allEntries = this.paymentMethodService.findAll();
//        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
//    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> save(@RequestBody PaymentMethodDto dto) {
        PaymentMethodCardEntity entity = convertToEntity(dto);
        paymentMethodService.saveCard(entity);
        return ResponseEntity.ok().build();
    }

}
