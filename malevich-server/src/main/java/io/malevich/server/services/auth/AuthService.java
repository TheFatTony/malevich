package io.malevich.server.services.auth;

import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);
}
