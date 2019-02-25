package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.transfer.DelayedChangeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/delayedChanges")
public class DelayedChangeResource extends RestResource<DelayedChangeDto, DelayedChangeEntity> {

    @Autowired
    private DelayedChangeService delayedChangeService;

    public DelayedChangeResource() {
        super(DelayedChangeDto.class, DelayedChangeEntity.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DelayedChangeDto> list() {
        List<DelayedChangeEntity> allEntries = this.delayedChangeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/approveChange", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void approveChange(@RequestBody DelayedChangeDto delayedChangeDto) {
        this.delayedChangeService.approveChange(convertToEntity(delayedChangeDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/declineChange", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void declineChange(@RequestBody DelayedChangeDto delayedChangeDto) {
        this.delayedChangeService.declineChange(convertToEntity(delayedChangeDto), delayedChangeDto.getComment());
    }

    @RequestMapping(value = "/findByTypeIdAndAndReferenceId/{typeId}/{referenceId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean findByTypeIdAndAndReferenceId(@PathVariable("typeId") String typeId, @PathVariable("referenceId") String referenceId) {
        DelayedChangeEntity allEntry = this.delayedChangeService.findByTypeIdAndAndReferenceId(typeId, referenceId);
        return (allEntry != null);
    }

}
