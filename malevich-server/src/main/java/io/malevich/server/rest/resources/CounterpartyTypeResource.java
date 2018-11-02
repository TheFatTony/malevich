package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.CounterpartyTypeEntity;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.transfer.CounterpartyTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/counterpartytype")
public class CounterpartyTypeResource {

    @Autowired
    private CounterpartyTypeService counterpartyTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(CounterpartyTypeDto.class)
    public List<CounterpartyTypeEntity> list() {
        List<CounterpartyTypeEntity> allEntries = this.counterpartyTypeService.findAll();
        return allEntries;
    }

}
