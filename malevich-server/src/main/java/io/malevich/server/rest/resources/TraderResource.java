package io.malevich.server.rest.resources;


import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderPersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/traders")
public class TraderResource extends RestResource<TraderPersonDto, TraderPersonEntity> {

    @Autowired
    private TraderService traderService;

    public TraderResource() {
        super(TraderPersonDto.class, TraderPersonEntity.class);
    }

    @PreAuthorize("hasRole('ROLE_TRADER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderPersonDto getTrader() {
        TraderPersonEntity traderEntity = traderService.getCurrentTrader();
        if (traderEntity == null)
            return null;
        return convertToDto(traderEntity);
    }

//    @PreAuthorize("hasRole('ROLE_TRADER')")
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResponseEntity<Void> update(@RequestBody TraderPersonDto trader) {
//        TraderPersonEntity newTraderEntity = convertToEntity(trader);
//        this.traderService.update(newTraderEntity);
//        return ResponseEntity.ok().build();
//    }

}
