package io.malevich.server.rest.resources;


import io.malevich.server.entity.UserEntity;
import io.malevich.server.services.auth.user.AuthService;
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
@RequestMapping(value = "/auth")
public class AuthResource {


    @Autowired
    private AuthService authService;


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public UserDto getUser() {
        return authService.getUser();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AccessTokenDto authenticate(@RequestBody LoginFormDto loginFormDto) {
        return authService.authenticate(loginFormDto);
    }


}
