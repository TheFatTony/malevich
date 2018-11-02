package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.transfer.GalleryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/galleries")
public class GalleryResource {

    @Autowired
    private GalleryService galleryService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(GalleryDto.class)
    public List<GalleryEntity> list() {
        List<GalleryEntity> allEntries = this.galleryService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(GalleryDto.class)
    public GalleryEntity item(@PathVariable("id") long id) {
        GalleryEntity allEntry = this.galleryService.find(id);
        return allEntry;
    }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(GalleryDto.class)
    public GalleryEntity current() {
        GalleryEntity allEntry = this.galleryService.getCurrent();
        return allEntry;
    }

    @PreAuthorize("hasRole('GALLERY')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> update(@RequestBody @DTO(GalleryDto.class) GalleryEntity gallery) {
        this.galleryService.update(gallery);
        return ResponseEntity.ok().build();
    }

}
