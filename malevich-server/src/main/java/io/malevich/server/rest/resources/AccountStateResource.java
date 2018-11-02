package io.malevich.server.rest.resources;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.transfer.AccountStateDto;
import io.malevich.server.transfer.ArtworkStockDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/accountstates")
public class AccountStateResource {

    @Autowired
    private AccountStateService accountStateService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AccountStateDto> list() {
        List<AccountStateEntity> allEntries = this.accountStateService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/getTraderWallet", method = RequestMethod.GET)
    public AccountStateDto getTraderWallet() {
        AccountStateEntity allEntry = this.accountStateService.getTraderWallet();
        if (allEntry == null)
            return null;
        return convertToDto(allEntry);
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/getTraderArtworks", method = RequestMethod.GET)
    public List<ArtworkStockDto> getTraderArtworks() {
        List<ArtworkStockEntity> allEntries = this.accountStateService.getTraderArtworks();
        return allEntries.stream().map(allEntry -> modelMapper.map(allEntry, ArtworkStockDto.class)).collect(Collectors.toList());
    }


    private AccountStateDto convertToDto(AccountStateEntity entity) {
        return modelMapper.map(entity, AccountStateDto.class);
    }

    private AccountStateEntity convertToEntity(AccountStateDto dto) {
        return modelMapper.map(dto, AccountStateEntity.class);
    }

}
