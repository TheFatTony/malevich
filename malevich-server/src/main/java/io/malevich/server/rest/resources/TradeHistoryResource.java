package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.transfer.TradeHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/trade_history")
public class TradeHistoryResource {

    @Autowired
    private TradeHistoryService tradeHistoryService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(TradeHistoryDto.class)
    public List<TradeHistoryEntity> list() {
        List<TradeHistoryEntity> allEntries = this.tradeHistoryService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/findAllByArtworkId/{artworkId}", method = RequestMethod.GET)
    @DTO(TradeHistoryDto.class)
    public List<TradeHistoryEntity> findAllByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<TradeHistoryEntity> allEntries = this.tradeHistoryService.findAllByArtworkId(artworkId);
        return allEntries;
    }

}
