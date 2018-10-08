package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {


    private String name;

    private String password;

    private List<String> roles;

    public UserDto() {

    }

    public UserDto(String userName, String password, List<String> roles) {
        this.name = userName;
        this.roles = roles;
        this.password = password;
    }

}