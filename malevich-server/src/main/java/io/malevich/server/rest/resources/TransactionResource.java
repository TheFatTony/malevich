package io.malevich.server.rest.resources;

import io.malevich.server.entity.TransactionEntity;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.transfer.TransactionsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
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
