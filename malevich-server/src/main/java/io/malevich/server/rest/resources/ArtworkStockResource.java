package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.repositories.artworkstock.filter.FilterSpecification;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.transfer.ArtworkStockDto;
import io.malevich.server.transfer.FilterDto;
import io.malevich.server.transfer.PageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/artworkstock")
public class ArtworkStockResource extends RestResource<ArtworkStockDto, ArtworkStockEntity> {

    @Autowired
    private ArtworkStockService artworkStockService;

    public ArtworkStockResource() {
        super(ArtworkStockDto.class, ArtworkStockEntity.class);
    }

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

    @PreAuthorize("hasAnyRole('ROLE_GALLERY', 'ROLE_TRADER')")
    @RequestMapping(value = "/getOwnArtworks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtworkStockDto> getOwnArtworks() {
        List<ArtworkStockEntity> allEntries = this.artworkStockService.getOwnArtworks();
        return convertListOfDto(allEntries);
    }

    @PreAuthorize("hasAnyRole('ROLE_GALLERY')")
    @RequestMapping(value = "/getStoredArtworks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtworkStockDto> getStoredArtworks() {
        List<ArtworkStockEntity> allEntries = this.artworkStockService.getStoredArtworks();
        return convertListOfDto(allEntries);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> add(@RequestBody ArtworkStockDto artworkStockDto) {
        ArtworkStockEntity entity = convertToEntity(artworkStockDto);
        artworkStockService.add(entity);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> update(@RequestBody ArtworkStockDto artworkStockDto) {
        ArtworkStockEntity entity = convertToEntity(artworkStockDto);
        artworkStockService.save(entity);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        artworkStockService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    // TODO exctarct to core
    public PageResponseDto filter(@RequestBody FilterDto filterDto) {
        Specification<ArtworkStockEntity> specification = new FilterSpecification(filterDto);
        Page<ArtworkStockEntity> resultPage = this.artworkStockService.findAll(specification, PageRequest.of(filterDto.getPage(), filterDto.getSize()));
        return new PageResponseDto(resultPage.getContent().stream().map(pageEntry -> convertToDto(pageEntry)).collect(Collectors.toList()), resultPage.getTotalElements(), resultPage.getTotalPages(), filterDto.getSort());
    }

}
