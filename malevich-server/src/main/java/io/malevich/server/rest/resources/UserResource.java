package io.malevich.server.rest.resources;


import com.yinyang.core.server.domain.RegisterTokenEntity;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.rest.RestResource;
import com.yinyang.core.server.services.user.UserService;
import com.yinyang.core.server.transfer.AccessTokenDto;
import com.yinyang.core.server.transfer.UserDto;
import io.malevich.server.services.register.RegisterService;
import io.malevich.server.transfer.*;
import lombok.extern.slf4j.Slf4j;
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
// TODO move most of the code into yinyang core
public class UserResource extends RestResource<UserDto, UserEntity> {


    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    public UserResource() {
        super(UserDto.class, UserEntity.class);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDto> list() {
        List<UserEntity> allEntries = this.userService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/lock")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> lock(@RequestBody UserDto lockDto) {
        this.userService.lock(lockDto.getName(), lockDto.isActivityFlag());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/password/set")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> setPassword(@RequestBody UserPasswordDto passwordDto) {
        this.userService.setPassword(passwordDto.getName(), passwordDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> registerStep1(@RequestBody RegisterFormDto registerFormDto, @RequestParam("lang") String lang) {
        RegisterTokenEntity registerTokenEntity = modelMapper.map(registerFormDto, RegisterTokenEntity.class);
        registerService.register(registerTokenEntity, lang);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register/{token}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccessTokenDto registerStep2(@RequestBody RegisterFormStepTwoDto resetDto, @PathVariable("token") String token) {
        return registerService.register2(token, resetDto);
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> reset(@RequestBody ResetPasswordFormDto resetFormDto) {
        userService.reset(resetFormDto.getLang(), resetFormDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/password/reset/{token}", method = RequestMethod.POST)
    public ResponseEntity<Void> reset(@RequestBody PasswordDto resetDto, @PathVariable("token") String token) {
        userService.setNewPassword(token, resetDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> changePassword(@RequestBody PasswordDto pwd) {
        userService.changePassword(pwd.getPassword(), pwd.getNewPassword());
        return ResponseEntity.ok().build();
    }

}
