package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.transfer.TradeHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/trade_history")
public class TradeHistoryResource extends RestResource<TradeHistoryDto, TradeHistoryEntity> {

    @Autowired
    private TradeHistoryService tradeHistoryService;

    public TradeHistoryResource() {
        super(TradeHistoryDto.class, TradeHistoryEntity.class);
    }


    @RequestMapping(value = "/findAllByArtworkId/{artworkId}", method = RequestMethod.GET)
    public List<TradeHistoryDto> findAllByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<TradeHistoryEntity> allEntries = this.tradeHistoryService.findAllByArtworkId(artworkId);
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
