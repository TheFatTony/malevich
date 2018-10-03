package io.malevich.server.rest.resources;


import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/traders")
public class TraderResource {

    @Autowired
    private TraderService traderService;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    /*@PreAuthorize("hasRole('USER')")*/ //todo hasRole('TRADER')
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public TraderDto getTrader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            TraderEntity traderEntity = traderService.findByUserName(username);
            return convertToDto(traderEntity);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public TraderDto item(@PathVariable("id") long id) {
        TraderEntity trader = this.traderService.find(id);
        return convertToDto(trader);
    }


    private TraderDto convertToDto(TraderEntity entity) {
        TraderDto dto = modelMapper.map(entity, TraderDto.class);
        return dto;
    }



}
