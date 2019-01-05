package io.malevich.server.services.user;

import io.malevich.server.domain.ResetPasswordTokenEntity;
import io.malevich.server.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    List<UserEntity> findAll();

    UserEntity getCurrent();

    UserEntity save(UserEntity userEntity);

    UserEntity findByName(String name);

    void lock(String name, boolean flag);

    void setPassword(String name, String pass);

    ResetPasswordTokenEntity reset(String lang, String userName);

    UserEntity setNewPassword(String token, String password);

    UserEntity changePassword(String oldPassword, String newPassword);
}
