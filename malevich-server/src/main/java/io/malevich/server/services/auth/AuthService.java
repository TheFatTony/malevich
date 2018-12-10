package io.malevich.server.services.auth;

import io.malevich.server.domain.RegisterTokenEntity;
import io.malevich.server.domain.ResetPasswordTokenEntity;
import io.malevich.server.domain.UserEntity;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDto getUser();

    AccessTokenDto authenticate(LoginFormDto loginFormDto);

    RegisterTokenEntity register(String lang, String userName);

    UserEntity register2(String token, String password);

    ResetPasswordTokenEntity reset(String lang, String userName);

    UserEntity setNewPassword(String token, String password);

    UserEntity changePassword(String oldPassword, String newPassword);
}
