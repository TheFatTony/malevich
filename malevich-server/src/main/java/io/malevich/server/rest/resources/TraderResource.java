package io.malevich.server.rest.resources;


import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/traders")
public class TraderResource {

    @Autowired
    private TraderService traderService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public TraderDto getTrader() {
        TraderEntity traderEntity = traderService.getCurrentTrader();
        if (traderEntity == null)
            return null;
        return convertToDto(traderEntity);
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
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
