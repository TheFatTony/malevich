package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.transfer.ArtworkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/artworks")
public class ArtworkResource {

    @Autowired
    private ArtworkService artworkService;


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(ArtworkDto.class)
    public List<ArtworkEntity> list() {
        List<ArtworkEntity> allEntries = this.artworkService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArtworkEntity item(@PathVariable("id") long id) {
        ArtworkEntity allEntry = this.artworkService.find(id);
        return allEntry;
    }

}
