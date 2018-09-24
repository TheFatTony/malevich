package io.malevich.server.services.auth.user;

import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthService extends UserDetailsService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);
}
