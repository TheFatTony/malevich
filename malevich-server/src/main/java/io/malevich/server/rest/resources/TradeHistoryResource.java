package io.malevich.server.rest.resources;

import io.malevich.server.domain.TradeHistoryEntity;
import io.malevich.server.services.tradehistory.TradeHistoryService;
import io.malevich.server.transfer.TradeHistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/trade_history")
public class TradeHistoryResource {

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TradeHistoryDto> list() {
        List<TradeHistoryEntity> allEntries = this.tradeHistoryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

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
