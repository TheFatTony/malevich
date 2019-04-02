package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ArtistEntity;
import io.malevich.server.services.artist.ArtistService;
import io.malevich.server.transfer.ArtistDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/artists")
public class ArtistResource extends RestResource<ArtistDto, ArtistEntity> {

    @Autowired
    private ArtistService artistService;

    public ArtistResource() {
        super(ArtistDto.class, ArtistEntity.class);
    }


    //    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ArtistDto> list() {
        List<ArtistEntity> allEntries = this.artistService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody ArtistDto artist) {
        this.artistService.save(convertToEntity(artist));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArtistDto item(@PathVariable("id") long id) {
        ArtistEntity allEntry = this.artistService.find(id);
        return convertToDto(allEntry);
    }

}
