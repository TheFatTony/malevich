package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.ArtistEntity;
import io.malevich.server.services.artist.ArtistService;
import io.malevich.server.transfer.ArtistDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/artists")
public class ArtistResource {

    @Autowired
    private ArtistService artistService;


    //    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(ArtistDto.class)
    public List<ArtistEntity> list() {
        List<ArtistEntity> allEntries = this.artistService.findAll();
        return allEntries;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(ArtistDto.class)
    public ArtistEntity item(@PathVariable("id") long id) {
        ArtistEntity allEntry = this.artistService.find(id);
        return allEntry;
    }

}
