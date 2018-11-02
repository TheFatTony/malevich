package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.TradeTypeEntity;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.transfer.TradeTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/tradetype")
public class TradeTypeResource {

    @Autowired
    private TradeTypeService tradeTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(TradeTypeDto.class)
    public List<TradeTypeEntity> list() {
        List<TradeTypeEntity> allEntries = this.tradeTypeService.findAll();
        return allEntries;
    }

}
