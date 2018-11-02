package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.transfer.AccountStateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/accountstates")
public class AccountStateResource {

    @Autowired
    private AccountStateService accountStateService;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(AccountStateDto.class)
    public List<AccountStateEntity> list() {
        List<AccountStateEntity> allEntries = this.accountStateService.findAll();
        return allEntries;
    }


    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/getTraderWallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(AccountStateDto.class)
    public AccountStateEntity getTraderWallet() {
        AccountStateEntity allEntry = this.accountStateService.getTraderWallet();
        if (allEntry == null)
            return null;
        return allEntry;
    }

    @PreAuthorize("hasRole('TRADER')")
    @RequestMapping(value = "/getTraderArtworks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(AccountStateDto.class)
    public List<ArtworkStockEntity> getTraderArtworks() {
        List<ArtworkStockEntity> allEntries = this.accountStateService.getTraderArtworks();
        return allEntries;
    }

}
