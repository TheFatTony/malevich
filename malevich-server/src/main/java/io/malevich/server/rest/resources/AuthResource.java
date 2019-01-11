package io.malevich.server.rest.resources;


import com.yinyang.core.server.services.auth.AuthService;
import com.yinyang.core.server.transfer.AccessTokenDto;
import com.yinyang.core.server.transfer.LoginFormDto;
import com.yinyang.core.server.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDto getUser() {
        return authService.getUser();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccessTokenDto authenticate(@RequestBody LoginFormDto loginFormDto) {
        return authService.authenticate(loginFormDto);
    }

}
