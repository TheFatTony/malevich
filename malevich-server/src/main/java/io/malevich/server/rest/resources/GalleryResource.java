package io.malevich.server.rest.resources;

import io.malevich.server.entity.GalleryEntity;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.transfer.GalleryDto;
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
@RequestMapping(value = "/galleries")
public class GalleryResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<GalleryDto> list() {
        this.logger.info("list()");
        List<GalleryEntity> allEntries = this.galleryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private GalleryDto convertToDto(GalleryEntity files) {
        GalleryDto filesDto = modelMapper.map(files, GalleryDto.class);
        return filesDto;
    }

    private GalleryEntity convertToEntity(GalleryDto filesDto) {
        GalleryEntity files = modelMapper.map(filesDto, GalleryEntity.class);
        return files;
    }

}
