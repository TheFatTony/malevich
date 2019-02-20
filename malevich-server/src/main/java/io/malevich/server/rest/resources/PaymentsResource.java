package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.aop.KycRequired;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.transfer.PaymentsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/payments")
public class PaymentsResource extends RestResource<PaymentsDto, PaymentsEntity> {

    @Autowired
    private PaymentsService paymentsService;

    public PaymentsResource() {
        super(PaymentsDto.class, PaymentsEntity.class);
    }

    @KycRequired(level = {KycLevel.G_TIER1, KycLevel.T_TIER1})
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentsDto> list() {
        List<PaymentsEntity> allEntries = this.paymentsService.findOwnPayments();
        return convertListOfDto(allEntries);
    }

    @GetMapping("/listByParticipant/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PaymentsDto> listByParticipant(@PathVariable("id") Long id) {
        List<PaymentsEntity> allEntries = this.paymentsService.findAllByParticipant(id);
        return convertListOfDto(allEntries);
    }

    @PostMapping("/insertAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> insertAdmin(@RequestBody PaymentsDto paymentsDto) {
        this.paymentsService.insertAdmin(convertToEntity(paymentsDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    public ResponseEntity<Void> insert(@RequestBody PaymentsDto paymentsDto) {
        this.paymentsService.insert(convertToEntity(paymentsDto));
        return ResponseEntity.ok().build();
    }

    @KycRequired(level = {KycLevel.G_TIER1, KycLevel.T_TIER1})
    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    @GetMapping("/print/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> print(@PathVariable("id") Long id) {
        PaymentsEntity entity = this.paymentsService.getPayments(id);
        return paymentsService.createFop(entity);
    }

}
