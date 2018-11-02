package io.malevich.server.rest.resources;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.transfer.GalleryDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/galleries")
public class GalleryResource {


    @Autowired
    private GalleryService galleryService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<GalleryDto> list() {
        List<GalleryEntity> allEntries = this.galleryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public GalleryDto item(@PathVariable("id") long id) {
        GalleryEntity allEntry = this.galleryService.find(id);
        return convertToDto(allEntry);
    }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public GalleryDto current() {
        log.info("happy");
        GalleryEntity allEntry = this.galleryService.getCurrent();
        return convertToDto(allEntry);
    }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> update(@RequestBody GalleryDto gallery) {
        GalleryEntity newEntity = convertToEntity(gallery);
        this.galleryService.update(newEntity);
        return ResponseEntity.ok().build();
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
