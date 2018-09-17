package io.malevich.server.transfer;

import java.util.List;


public class UserDto {

    private  String name;

    private  String password;

    private  List<String> roles;

    public UserDto() {

    }

    public UserDto(String userName,String password, List<String> roles) {
        this.name = userName;
        this.roles = roles;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public String getPassword() {
        return password;
    }

}