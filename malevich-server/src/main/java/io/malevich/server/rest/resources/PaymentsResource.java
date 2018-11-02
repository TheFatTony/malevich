package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.transfer.PaymentsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/payments")
public class PaymentsResource {

    @Autowired
    private PaymentsService paymentsService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(PaymentsDto.class)
    public List<PaymentsEntity> list() {
        List<PaymentsEntity> allEntries = this.paymentsService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@DTO(PaymentsDto.class) @RequestBody PaymentsEntity paymentsDto) {
        this.paymentsService.insert(paymentsDto);
        return ResponseEntity.ok().build();
    }

}
