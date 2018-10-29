package io.malevich.server.rest.resources;

import io.malevich.server.entity.PaymentsEntity;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.transfer.PaymentsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/payments")
public class PaymentsResource {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PaymentsDto> list() {
        List<PaymentsEntity> allEntries = this.paymentsService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody PaymentsDto paymentsDto) {
        this.paymentsService.insert(convertToEntity(paymentsDto));
        return ResponseEntity.ok().build();
    }

    private PaymentsDto convertToDto(PaymentsEntity entity) {
        return modelMapper.map(entity, PaymentsDto.class);
    }

    private PaymentsEntity convertToEntity(PaymentsDto dto) {
        return modelMapper.map(dto, PaymentsEntity.class);
    }

}
