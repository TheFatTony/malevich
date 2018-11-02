package io.malevich.server.rest.resources;

import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.transfer.ArtworkDto;
import io.malevich.server.transfer.FileDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/artworks")
public class ArtworkResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ArtworkDto> list() {
        this.logger.info("list()");
        List<ArtworkEntity> allEntries = this.artworkService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ArtworkDto item(@PathVariable("id") long id) {
        this.logger.info("item()");
        ArtworkEntity allEntry = this.artworkService.find(id);
        return convertToDto(allEntry);
    }


    private ArtworkDto convertToDto(ArtworkEntity files) {
        ArtworkDto filesDto = modelMapper.map(files, ArtworkDto.class);
        return filesDto;
    }

    private ArtworkEntity convertToEntity(FileDto filesDto) {
        ArtworkEntity files = modelMapper.map(filesDto, ArtworkEntity.class);
        return files;
    }

}
