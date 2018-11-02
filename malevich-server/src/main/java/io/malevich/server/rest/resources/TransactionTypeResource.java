package io.malevich.server.rest.resources;

import io.malevich.server.domain.TransactionTypeEntity;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import io.malevich.server.transfer.TransactionTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/transactiontype")
public class TransactionTypeResource {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionTypeDto> list() {
        List<TransactionTypeEntity> allEntries = this.transactionTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private TransactionTypeDto convertToDto(TransactionTypeEntity entity) {
        return modelMapper.map(entity, TransactionTypeDto.class);
    }

    private TransactionTypeEntity convertToEntity(TransactionTypeDto dto) {
        return modelMapper.map(dto, TransactionTypeEntity.class);
    }

}
