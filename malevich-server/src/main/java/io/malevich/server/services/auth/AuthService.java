package io.malevich.server.services.auth;

import io.malevich.server.entity.RegisterTokenEntity;
import io.malevich.server.entity.ResetPasswordTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AuthService extends UserDetailsService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);

    RegisterTokenEntity register(String lang, String userName);

    UserEntity register2(String token, String password);

    ResetPasswordTokenEntity reset(String lang, String userName);

    UserEntity setNewPassword(String token, String password);
}
