package io.malevich.server.rest.resources;


import io.malevich.server.entity.RegisterTokenEntity;
import io.malevich.server.entity.UserTypeEntity;
import io.malevich.server.services.auth.AuthService;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.RegisterFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody RegisterFormDto registerFormDto) {
        authService.register(registerFormDto.getLang(), registerFormDto.getEmail());
        return ResponseEntity.ok().body("registred");
    }


}
