package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ParameterEntity;
import io.malevich.server.services.parameter.ParameterService;
import io.malevich.server.transfer.ParameterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/parameter")
public class ParameterResource extends RestResource<ParameterDto, ParameterEntity> {

    @Autowired
    private ParameterService parameterService;

    public ParameterResource() {
        super(ParameterDto.class, ParameterEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParameterDto> list() {
        List<ParameterEntity> allEntries = this.parameterService.findAll();
        return convertListOfDto(allEntries);
    }


}
