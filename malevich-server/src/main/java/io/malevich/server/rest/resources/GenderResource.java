package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.GenderEntity;
import io.malevich.server.domain.ParameterEntity;
import io.malevich.server.services.gender.GenderService;
import io.malevich.server.services.parameter.ParameterService;
import io.malevich.server.transfer.GenderDto;
import io.malevich.server.transfer.ParameterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/genders")
public class GenderResource extends RestResource<GenderDto, GenderEntity> {

    @Autowired
    private GenderService genderService;

    public GenderResource() {
        super(GenderDto.class, GenderEntity.class);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GenderDto> list() {
        List<GenderEntity> allEntries = this.genderService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


}

