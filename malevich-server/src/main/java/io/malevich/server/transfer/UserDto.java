package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class UserDto {


    @Getter
    @Setter
    private String name;


    @Getter
    @Setter
    private String password;


    @Getter
    @Setter
    private List<String> roles;

    public UserDto() {

    }

    public UserDto(String userName, String password, List<String> roles) {
        this.name = userName;
        this.roles = roles;
        this.password = password;
    }

}