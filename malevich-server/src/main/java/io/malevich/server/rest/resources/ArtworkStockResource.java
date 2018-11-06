package io.malevich.server.rest.resources;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.transfer.ArtworkStockDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/artworkstock")
public class ArtworkStockResource {

    @Autowired
    private ArtworkStockService artworkStockService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtworkStockDto> list() {
        List<ArtworkStockEntity> allEntries = this.artworkStockService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArtworkStockDto item(@PathVariable("id") long id) {
        ArtworkStockEntity allEntry = this.artworkStockService.find(id);
        return convertToDto(allEntry);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> add(@RequestBody ArtworkStockDto artworkStockDto) {
        ArtworkStockEntity entity = convertToEntity(artworkStockDto);
        artworkStockService.add(entity);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> add(@PathVariable("id") long id) {
        artworkStockService.delete(id);
        return ResponseEntity.ok().build();
    }

    private ArtworkStockDto convertToDto(ArtworkStockEntity entity) {
        return modelMapper.map(entity, ArtworkStockDto.class);
    }

    private ArtworkStockEntity convertToEntity(ArtworkStockDto dto) {
        return modelMapper.map(dto, ArtworkStockEntity.class);
    }

}
