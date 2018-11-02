package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.TransactionTypeEntity;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import io.malevich.server.transfer.TransactionTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/transactiontype")
public class TransactionTypeResource {

    @Autowired
    private TransactionTypeService transactionTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(TransactionTypeDto.class)
    public List<TransactionTypeEntity> list() {
        List<TransactionTypeEntity> allEntries = this.transactionTypeService.findAll();
        return allEntries;
    }

}
