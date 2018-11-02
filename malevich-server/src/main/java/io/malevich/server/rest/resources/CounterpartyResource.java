package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.transfer.CounterpartyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/counterparties")
public class CounterpartyResource {

    @Autowired
    private CounterpartyService counterpartyService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(CounterpartyDto.class)
    public List<CounterpartyEntity> list() {
        List<CounterpartyEntity> allEntries = this.counterpartyService.findAll();
        return allEntries;
    }

}
