package io.malevich.server.rest.resources;

import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.transfer.TradeHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/trade_history")
public class TradeHistoryResource {

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/findAllByArtworkId/{artworkId}", method = RequestMethod.GET)
    public List<TradeHistoryDto> findAllByArtworkId(@PathVariable("artworkId") long artworkId) {
        List<TradeHistoryEntity> allEntries = this.tradeHistoryService.findAllByArtworkId(artworkId);
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private TradeHistoryDto convertToDto(TradeHistoryEntity entity) {
        return modelMapper.map(entity, TradeHistoryDto.class);
    }

    private TradeHistoryEntity convertToEntity(TradeHistoryDto dto) {
        return modelMapper.map(dto, TradeHistoryEntity.class);
    }

}
