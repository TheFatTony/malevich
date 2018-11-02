package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.GenderEntity;
import io.malevich.server.services.gender.GenderService;
import io.malevich.server.transfer.GenderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/genders")
public class GenderResource {

    @Autowired
    private GenderService genderService;


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(GenderDto.class)
    public List<GenderEntity> list() {
        List<GenderEntity> allEntries = this.genderService.findAll();
        return allEntries;
    }

}
