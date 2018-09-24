package io.malevich.server.services.user;

import io.malevich.server.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserEntity> findAll();
}
