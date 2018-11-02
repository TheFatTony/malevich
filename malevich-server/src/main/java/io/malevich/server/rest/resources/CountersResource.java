package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.InvolvementEntity;
import io.malevich.server.services.counters.CountersService;
import io.malevich.server.transfer.CounterpartyTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/counters")
public class CountersResource {

    @Autowired
    private CountersService countersService;


    //    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/involvementCounters", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(CounterpartyTypeDto.class)
    public InvolvementEntity getInvolvementCounters() {
        InvolvementEntity allEntry = this.countersService.getInvolvementCounters();
        return allEntry;
    }

}
