package io.malevich.server.rest.resources;


import io.malevich.server.domain.UserEntity;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.UserDto;
import io.malevich.server.transfer.UserPasswordDto;
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
@RequestMapping(value = "/users")
public class UserResource {


    @Autowired
    private UserService userService;

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
    @PostMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDto passwordDto) {
        this.userService.changePassword(passwordDto.getName(), passwordDto.getPassword());
        return ResponseEntity.ok().build();
    }


    private UserDto convertToDto(UserEntity entity) {
        return modelMapper.map(entity, UserDto.class);
    }
}
