package io.malevich.server.services.auth;

import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.transfer.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthService extends UserDetailsService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);

    RegisterTokenDto register(String userName);
}
