package io.malevich.server.rest.resources;

import io.malevich.server.entity.ArtworkStockEntity;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.transfer.ArtworkStockDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/artworkstock")
public class ArtworkStockResource {

  @Autowired
  private ArtworkStockService artworkStockService;

  @Autowired
  private ModelMapper modelMapper;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<ArtworkStockDto> list() {
  List<ArtworkStockEntity> allEntries = this.artworkStockService.findAll();
    return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
  }

  private ArtworkStockDto convertToDto(ArtworkStockEntity entity) {
    return modelMapper.map(entity, ArtworkStockDto.class);
  }

  private ArtworkStockEntity convertToEntity(ArtworkStockDto dto) {
    return modelMapper.map(dto, ArtworkStockEntity.class);
  }

}
