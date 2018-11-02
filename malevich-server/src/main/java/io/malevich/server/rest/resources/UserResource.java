package io.malevich.server.rest.resources;


import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.UserEntity;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(UserDto.class)
    public List<UserEntity> list() {
        List<UserEntity> allEntries = this.userService.findAll();
        return allEntries;
    }

}
