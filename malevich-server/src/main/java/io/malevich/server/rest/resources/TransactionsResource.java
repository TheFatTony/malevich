package com.sample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import java.util.List;
import java.util.List;
import java.util.List;
import java.util.List;
import java.util.List;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class TransactionsResource {

  @Autowired
  private TransactionsService service;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<TransactionsDto> list() {
  List<TransactionsEntity> allEntries = this.service.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private TransactionsDto convertToDto(TransactionsEntity entity) {
    return modelMapper.map(entity, TransactionsDto.class);
  }

  private TransactionsEntity convertToEntity(TransactionsDto dto) {
    return modelMapper.map(dto, TransactionsEntity.class);
  }

}
