package io.malevich.server.rest.resources;

import io.malevich.server.entity.AccountStateEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.transfer.AccountStateDto;
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
    @RequestMapping(value = "/getTraderAccountState", method = RequestMethod.GET)
    public AccountStateDto getTraderAccountState() {
        AccountStateEntity allEntry = this.accountStateService.getTraderAccountState();
        if (allEntry == null)
            return null;
        return convertToDto(allEntry);
    }


    private AccountStateDto convertToDto(AccountStateEntity entity) {
        return modelMapper.map(entity, AccountStateDto.class);
    }

    private AccountStateEntity convertToEntity(AccountStateDto dto) {
        return modelMapper.map(dto, AccountStateEntity.class);
    }

}
