package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

public class AccessTokenDto {

    @Getter @Setter
    private String token;


    public AccessTokenDto(String token) {
        this.token = token;
    }

}
