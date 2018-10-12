package io.malevich.server.rest.resources;

import io.malevich.server.entity.TradeTypeEntity;
import io.malevich.server.services.tradetype.TradeTypeService;
import io.malevich.server.transfer.TradeTypeDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class TradeTypeResource {

  @Autowired
  private TradeTypeService tradeTypeService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<TradeTypeDto> list() {
  List<TradeTypeEntity> allEntries = this.tradeTypeService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private TradeTypeDto convertToDto(TradeTypeEntity entity) {
    return modelMapper.map(entity, TradeTypeDto.class);
  }

  private TradeTypeEntity convertToEntity(TradeTypeDto dto) {
    return modelMapper.map(dto, TradeTypeEntity.class);
  }

}
