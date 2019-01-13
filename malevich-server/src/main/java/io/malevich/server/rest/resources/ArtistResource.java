package io.malevich.server.rest.resources;

import io.malevich.server.domain.ArtistEntity;
import io.malevich.server.services.artist.ArtistService;
import io.malevich.server.transfer.ArtistDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/artists")
public class ArtistResource {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtistDto> list() {
        List<ArtistEntity> allEntries = this.artistService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArtistDto item(@PathVariable("id") long id) {
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
