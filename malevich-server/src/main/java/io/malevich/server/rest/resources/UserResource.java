package io.malevich.server.rest.resources;


import io.malevich.server.domain.UserEntity;
import io.malevich.server.services.registertoken.RegisterService;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserResource {


    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ModelMapper modelMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDto> list() {
        List<UserEntity> allEntries = this.userService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/lock")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> lock(@RequestBody UserDto lockDto) {
        this.userService.lock(lockDto.getName(), lockDto.isActivityFlag());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/password/set")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> setPassword(@RequestBody UserPasswordDto passwordDto) {
        this.userService.setPassword(passwordDto.getName(), passwordDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> registerStep1(@RequestBody RegisterFormDto registerFormDto) {
        registerService.register(registerFormDto.getLang(), registerFormDto.getEmail());
        return ResponseEntity.ok().body("registered");
    }

    @RequestMapping(value = "/register/{token}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> registerStep2(@RequestBody RegisterFormStepTwoDto resetDto, @PathVariable("token") String token) {
        registerService.register2(token, resetDto);
        return ResponseEntity.ok().body("password set");
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> reset(@RequestBody ResetPasswordFormDto resetFormDto) {
        userService.reset(resetFormDto.getLang(), resetFormDto.getEmail());
        return ResponseEntity.ok().body("reset");
    }

    @RequestMapping(value = "/password/reset/{token}", method = RequestMethod.POST)
    public ResponseEntity<String> reset(@RequestBody PasswordDto resetDto, @PathVariable("token") String token) {
        userService.setNewPassword(token, resetDto.getPassword());
        return ResponseEntity.ok().body("password set");
    }

    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody PasswordDto pwd) {
        userService.changePassword(pwd.getPassword(), pwd.getNewPassword());
        return ResponseEntity.ok().body("password changed");
    }

    private UserDto convertToDto(UserEntity entity) {
        return modelMapper.map(entity, UserDto.class);
    }
}
