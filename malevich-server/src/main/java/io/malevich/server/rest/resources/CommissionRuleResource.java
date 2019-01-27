package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.CommissionRuleEntity;
import io.malevich.server.services.commissionrule.CommissionRuleService;
import io.malevich.server.transfer.CommissionRuleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/commission")
public class CommissionRuleResource extends RestResource<CommissionRuleDto, CommissionRuleEntity> {

    @Autowired
    private CommissionRuleService commissionRuleService;

    public CommissionRuleResource() {
        super(CommissionRuleDto.class, CommissionRuleEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CommissionRuleDto> list() {
        List<CommissionRuleEntity> allEntries = this.commissionRuleService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
