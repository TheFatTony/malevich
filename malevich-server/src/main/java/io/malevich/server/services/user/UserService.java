package io.malevich.server.services.user;

import io.malevich.server.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserService {

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

    UserEntity findByName(String name);
}
