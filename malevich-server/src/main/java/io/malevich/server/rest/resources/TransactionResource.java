package io.malevich.server.rest.resources;

import io.malevich.server.domain.TransactionEntity;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.transfer.TransactionsDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionsDto> list() {
        List<TransactionEntity> allEntries = this.transactionService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private TransactionsDto convertToDto(TransactionEntity entity) {
        return modelMapper.map(entity, TransactionsDto.class);
    }

    private TransactionEntity convertToEntity(TransactionsDto dto) {
        return modelMapper.map(dto, TransactionEntity.class);
    }

}
