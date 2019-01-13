package io.malevich.server.rest.resources;

import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.transfer.PaymentsDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/payments")
public class PaymentsResource {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentsDto> list() {
        List<PaymentsEntity> allEntries = this.paymentsService.findOwnPayments();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PostMapping("/insert")
    public ResponseEntity<Void> insert(@RequestBody PaymentsDto paymentsDto) {
        this.paymentsService.insertPayment(convertToEntity(paymentsDto));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    @GetMapping("/print/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> print(@PathVariable("id") Long id) {
        PaymentsEntity entity = this.paymentsService.getPayments(id);
        return paymentsService.createFop(entity);
    }

    private PaymentsDto convertToDto(PaymentsEntity entity) {
        return modelMapper.map(entity, PaymentsDto.class);
    }

    private PaymentsEntity convertToEntity(PaymentsDto dto) {
        return modelMapper.map(dto, PaymentsEntity.class);
    }

}
