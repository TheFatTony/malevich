package io.malevich.server.rest.resources;


import io.malevich.server.domain.TraderEntity;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderDto getTrader() {
        TraderEntity traderEntity = traderService.getCurrentTrader();
        if (traderEntity == null)
            return null;
        return convertToDto(traderEntity);
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> update(@RequestBody TraderDto trader) {
        TraderEntity newTraderEntity = convertToEntity(trader);
        this.traderService.update(newTraderEntity);
        return ResponseEntity.ok().build();
    }

    private TraderDto convertToDto(TraderEntity entity) {
        TraderDto dto = modelMapper.map(entity, TraderDto.class);
        return dto;
    }

    private TraderEntity convertToEntity(TraderDto filesDto) {
        TraderEntity files = modelMapper.map(filesDto, TraderEntity.class);
        return files;
    }

}
