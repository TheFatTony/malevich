package io.malevich.server.transfer;

import lombok.Getter;

public class TokenDto {


    @Getter
    private final String token;

    public TokenDto(String token) {
        this.token = token;
    }

}