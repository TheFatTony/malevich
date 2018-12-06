package io.malevich.server.services.user;

import io.malevich.server.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

    UserEntity findByName(String name);

    void lock(String name, boolean flag);

    void changePassword(String name, String pass);
}
