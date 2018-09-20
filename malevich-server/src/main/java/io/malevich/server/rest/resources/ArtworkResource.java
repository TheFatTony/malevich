package io.malevich.server.rest.resources;

import io.malevich.server.entity.ArtworkEntity;
import io.malevich.server.services.artwork.category.ArtworkService;
import io.malevich.server.services.category.CategoryService;
import io.malevich.server.transfer.ArtworkDto;
import io.malevich.server.transfer.FileDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ArtworkDto convertToDto(ArtworkEntity files) {
        ArtworkDto filesDto = modelMapper.map(files, ArtworkDto.class);
        return filesDto;
    }

    private ArtworkEntity convertToEntity(FileDto filesDto) {
        ArtworkEntity files = modelMapper.map(filesDto, ArtworkEntity.class);
        return files;
    }

}
