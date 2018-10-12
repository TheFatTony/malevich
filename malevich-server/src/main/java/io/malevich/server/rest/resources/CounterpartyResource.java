package io.malevich.server.rest.resources;

import io.malevich.server.entity.CounterpartyEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.transfer.CounterpartyDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CounterpartyResource {

  @Autowired
  private CounterpartyService counterpartyService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<CounterpartyDto> list() {
  List<CounterpartyEntity> allEntries = this.counterpartyService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private CounterpartyDto convertToDto(CounterpartyEntity entity) {
    return modelMapper.map(entity, CounterpartyDto.class);
  }

  private CounterpartyEntity convertToEntity(CounterpartyDto dto) {
    return modelMapper.map(dto, CounterpartyEntity.class);
  }

}
