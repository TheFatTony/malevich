package io.malevich.server.rest.resources;

import io.malevich.server.domain.ArtistEntity;
import io.malevich.server.services.artist.ArtistService;
import io.malevich.server.transfer.ArtistDto;
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
@RequestMapping(value = "/artists")
public class ArtistResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ArtistDto> list() {
        this.logger.info("list()");
        List<ArtistEntity> allEntries = this.artistService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ArtistDto item(@PathVariable("id") long id) {
        this.logger.info("item()");
        ArtistEntity allEntry = this.artistService.find(id);
        return convertToDto(allEntry);
    }

    private ArtistDto convertToDto(ArtistEntity files) {
        ArtistDto filesDto = modelMapper.map(files, ArtistDto.class);
        return filesDto;
    }

    private ArtistEntity convertToEntity(ArtistDto filesDto) {
        ArtistEntity files = modelMapper.map(filesDto, ArtistEntity.class);
        return files;
    }

}
