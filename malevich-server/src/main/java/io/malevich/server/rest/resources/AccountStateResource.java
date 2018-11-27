package io.malevich.server.rest.resources;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.transfer.AccountStateDto;
import io.malevich.server.transfer.ArtworkStockDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/accountstates")
public class AccountStateResource {

    @Autowired
    private AccountStateService accountStateService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AccountStateDto> list() {
        List<AccountStateEntity> allEntries = this.accountStateService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/getTraderWallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountStateDto getTraderWallet() {
        AccountStateEntity allEntry = this.accountStateService.getTraderWallet();
        if (allEntry == null)
            return null;
        return convertToDto(allEntry);
    }

    @PreAuthorize("hasAnyRole('GALLERY', 'TRADER')")
    @RequestMapping(value = "/getOwnArtworks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtworkStockDto> getOwnArtworks() {
        List<ArtworkStockEntity> allEntries = this.accountStateService.getOwnArtworks();
        return allEntries.stream().map(allEntry -> modelMapper.map(allEntry, ArtworkStockDto.class)).collect(Collectors.toList());
    }


    private AccountStateDto convertToDto(AccountStateEntity entity) {
        return modelMapper.map(entity, AccountStateDto.class);
    }

    private AccountStateEntity convertToEntity(AccountStateDto dto) {
        return modelMapper.map(dto, AccountStateEntity.class);
    }

}
