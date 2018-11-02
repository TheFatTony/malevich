package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.TransactionEntity;
import io.malevich.server.services.transaction.TransactionService;
import io.malevich.server.transfer.TransactionsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(TransactionsDto.class)
    public List<TransactionEntity> list() {
        List<TransactionEntity> allEntries = this.transactionService.findAll();
        return allEntries;
    }

}
