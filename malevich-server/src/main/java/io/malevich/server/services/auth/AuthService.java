package io.malevich.server.services.auth;

import io.malevich.server.entity.RegisterTokenEntity;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthService extends UserDetailsService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);

    RegisterTokenEntity register(String lang, String userName);
}
