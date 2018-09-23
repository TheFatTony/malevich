package io.malevich.server.dao.user;


import io.malevich.server.dao.Dao;
import io.malevich.server.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends Dao<UserEntity, Long> {
    UserEntity loadUserByUsername(String username);

    UserEntity findByName(String name);
}
