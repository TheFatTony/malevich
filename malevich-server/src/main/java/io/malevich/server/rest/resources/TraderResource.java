package io.malevich.server.rest.resources;


import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.TraderEntity;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/traders")
public class TraderResource {

    @Autowired
    private TraderService traderService;

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(TraderDto.class)
    public TraderEntity getTrader() {
        TraderEntity traderEntity = traderService.getCurrentTrader();
        return traderEntity;
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> update(@DTO(TraderDto.class) @RequestBody TraderEntity trader) {
        this.traderService.update(trader);
        return ResponseEntity.ok().build();
    }

}
