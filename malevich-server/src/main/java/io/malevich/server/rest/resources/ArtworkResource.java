package io.malevich.server.rest.resources;

import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.transfer.ArtworkDto;
import io.malevich.server.transfer.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/artworks")
public class ArtworkResource {

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtworkDto> list() {
        List<ArtworkEntity> allEntries = this.artworkService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArtworkDto item(@PathVariable("id") long id) {
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
