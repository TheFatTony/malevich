package io.malevich.server.rest.resources;

import io.malevich.server.entity.TransactionTypeEntity;
import io.malevich.server.services.transactiontype.TransactionTypeService;
import io.malevich.server.transfer.TransactionTypeDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping(value = "/transactiontype")
public class TransactionTypeResource {

  @Autowired
  private TransactionTypeService transactionTypeService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<TransactionTypeDto> list() {
  List<TransactionTypeEntity> allEntries = this.transactionTypeService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private TransactionTypeDto convertToDto(TransactionTypeEntity entity) {
    return modelMapper.map(entity, TransactionTypeDto.class);
  }

  private TransactionTypeEntity convertToEntity(TransactionTypeDto dto) {
    return modelMapper.map(dto, TransactionTypeEntity.class);
  }

}
