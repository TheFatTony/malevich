package io.malevich.server.transfer;

public class TokenDto {

    private final String token;


    public TokenDto(String token) {
        this.token = token;
    }


    public String getToken() {
        return this.token;
    }

}