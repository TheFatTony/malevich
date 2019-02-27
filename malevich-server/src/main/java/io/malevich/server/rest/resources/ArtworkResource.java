package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.transfer.ArtworkDto;
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
@RequestMapping(value = "/artworks")
public class ArtworkResource extends RestResource<ArtworkDto, ArtworkEntity> {

    @Autowired
    private ArtworkService artworkService;

    public ArtworkResource() {
        super(ArtworkDto.class, ArtworkEntity.class);
    }


    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> save(@RequestBody ArtworkDto artwork) {
        this.artworkService.save(convertToEntity(artwork));
        return ResponseEntity.ok().build();
    }

}
