package io.malevich.server.rest.resources;


import io.malevich.server.services.auth.AuthService;
import io.malevich.server.transfer.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasRole('USER')")
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RegisterFormDto registerFormDto) {
        authService.register(registerFormDto.getLang(), registerFormDto.getEmail());
        return ResponseEntity.ok().body("registered");
    }

    @RequestMapping(value = "/register/{token}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody PasswordDto resetDto, @PathVariable("token") String token) {
        authService.register2(token, resetDto.getPassword());
        return ResponseEntity.ok().body("password set");
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> reset(@RequestBody ResetPasswordFormDto resetFormDto) {
        authService.reset(resetFormDto.getLang(), resetFormDto.getEmail());
        return ResponseEntity.ok().body("reset");
    }

    @RequestMapping(value = "/reset/{token}", method = RequestMethod.POST)
    public ResponseEntity<String> reset(
            @RequestBody PasswordDto resetDto,
            @PathVariable("token") String token
    ) {
        authService.setNewPassword(token, resetDto.getPassword());
        return ResponseEntity.ok().body("password set");
    }

}
