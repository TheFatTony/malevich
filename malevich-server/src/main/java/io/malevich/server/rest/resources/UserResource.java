package io.malevich.server.rest.resources;


import io.malevich.server.entity.UserEntity;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/users")
public class UserResource {


    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserDto> list() {
        List<UserEntity> allEntries = this.userService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }


    private UserDto convertToDto(UserEntity entity) {
        UserDto dto = modelMapper.map(entity, UserDto.class);
        return dto;
    }
}
