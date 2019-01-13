package io.malevich.server.rest.resources;


import com.yinyang.core.server.domain.UserTypeEntity;
import com.yinyang.core.server.services.usertype.UserTypeService;
import com.yinyang.core.server.transfer.UserTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/userType")
public class UserTypeResource {


    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserTypeDto> list() {
        List<UserTypeEntity> allEntries = this.userTypeService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private UserTypeDto convertToDto(UserTypeEntity entity) {
        return modelMapper.map(entity, UserTypeDto.class);
    }
}
