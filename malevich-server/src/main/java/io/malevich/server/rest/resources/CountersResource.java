package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.InvolvementEntity;
import io.malevich.server.services.counters.CountersService;
import io.malevich.server.transfer.InvolvementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/counters")
public class CountersResource extends RestResource<InvolvementDto, InvolvementEntity> {


    @Autowired
    private CountersService countersService;

    public CountersResource() {
        super(InvolvementDto.class, InvolvementEntity.class);
    }


    //    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/involvementCounters", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public InvolvementDto getInvolvementCounters() {
        InvolvementEntity allEntry = this.countersService.getInvolvementCounters();
        return convertToDto(allEntry);
    }

}
