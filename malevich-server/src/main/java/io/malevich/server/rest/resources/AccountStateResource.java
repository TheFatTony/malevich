package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.transfer.AccountStateDto;
import io.malevich.server.transfer.ArtworkStockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/accountstates")
public class AccountStateResource extends RestResource<AccountStateDto, AccountStateEntity> {

    @Autowired
    private AccountStateService accountStateService;


    public AccountStateResource() {
        super(AccountStateDto.class, AccountStateEntity.class);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/getWallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountStateDto getWallet() {
        AccountStateEntity allEntry = this.accountStateService.getWallet();
        if (allEntry == null)
            return null;
        return convertToDto(allEntry);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AccountStateDto> list() {
        List<AccountStateEntity> allEntry = this.accountStateService.getAll();
        return convertListOfDto(allEntry);
    }


}
