package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.KycLevelEntity;
import io.malevich.server.services.kyc.KycLevelService;
import io.malevich.server.transfer.KycLevelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/kycLevels")
public class KycLevelResource extends RestResource<KycLevelDto, KycLevelEntity> {

    @Autowired
    private KycLevelService kycLevelService;

    public KycLevelResource() {
        super(KycLevelDto.class, KycLevelEntity.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<KycLevelDto> list() {
        List<KycLevelEntity> allEntries = this.kycLevelService.findAll();
        return convertListOfDto(allEntries);
    }

    @RequestMapping(value = "/detailed/{level}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<KycLevelDto> detailed(@PathVariable String level) {
        List<KycLevelEntity> allEntries = this.kycLevelService.getDetailing(level);
        return convertListOfDto(allEntries);
    }
}
