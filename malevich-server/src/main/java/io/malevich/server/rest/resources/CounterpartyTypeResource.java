package io.malevich.server.rest.resources;

import io.malevich.server.entity.CounterpartyTypeEntity;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.transfer.CounterpartyTypeDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CounterpartyTypeResource {

  @Autowired
  private CounterpartyTypeService counterpartyTypeService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<CounterpartyTypeDto> list() {
  List<CounterpartyTypeEntity> allEntries = this.counterpartyTypeService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private CounterpartyTypeDto convertToDto(CounterpartyTypeEntity entity) {
    return modelMapper.map(entity, CounterpartyTypeDto.class);
  }

  private CounterpartyTypeEntity convertToEntity(CounterpartyTypeDto dto) {
    return modelMapper.map(dto, CounterpartyTypeEntity.class);
  }

}
