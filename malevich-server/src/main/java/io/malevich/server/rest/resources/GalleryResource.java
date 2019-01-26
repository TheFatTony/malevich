package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.transfer.GalleryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/galleries")
public class GalleryResource extends RestResource<GalleryDto, GalleryEntity> {


    @Autowired
    private GalleryService galleryService;

    public GalleryResource() {
        super(GalleryDto.class, GalleryEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GalleryDto> list() {
        List<GalleryEntity> allEntries = this.galleryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GalleryDto item(@PathVariable("id") long id) {
        GalleryEntity allEntry = this.galleryService.find(id);
        return convertToDto(allEntry);
    }

}
