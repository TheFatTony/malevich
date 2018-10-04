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


    private TraderEntity getCurrentTrader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        TraderEntity traderEntity = traderService.findByUserName(username);
        return traderEntity;
    }

    @Transactional
    /*@PreAuthorize("hasRole('USER')")*/ //todo hasRole('TRADER')
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public TraderDto getTrader() {
        TraderEntity traderEntity = this.getCurrentTrader();
        return convertToDto(traderEntity);
    }

/*    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public TraderDto item(@PathVariable("id") long id) {
        TraderEntity trader = this.traderService.find(id);
        return convertToDto(trader);
    }*/

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody TraderDto trader){
        TraderEntity traderEntity = getCurrentTrader();
        TraderEntity newTraderEntity = convertToEntity(trader);
        newTraderEntity.setId(traderEntity.getId());
        newTraderEntity.getUser().setId(traderEntity.getUser().getId());
        newTraderEntity.getPerson().setId(traderEntity.getPerson().getId());
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
