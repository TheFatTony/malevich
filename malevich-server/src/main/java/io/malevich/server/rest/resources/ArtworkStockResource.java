package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.transfer.ArtworkStockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/artworkstock")
public class ArtworkStockResource {

    @Autowired
    private ArtworkStockService artworkStockService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(ArtworkStockDto.class)
    public List<ArtworkStockEntity> list() {
        List<ArtworkStockEntity> allEntries = this.artworkStockService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(ArtworkStockDto.class)
    public ArtworkStockEntity item(@PathVariable("id") long id) {
        ArtworkStockEntity allEntry = this.artworkStockService.find(id);
        return allEntry;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> add(@DTO(ArtworkStockDto.class) @RequestBody ArtworkStockEntity artworkStockEntity) {
        artworkStockService.add(artworkStockEntity);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> add(@PathVariable("id") long id) {
        artworkStockService.delete(id);
        return ResponseEntity.ok().build();
    }

}
