package io.malevich.server.services.user;

import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserEntity findUserByAccessToken(String accessToken);

    AccessTokenEntity createAccessToken(UserEntity user);

    List<UserEntity> findAll();
}
