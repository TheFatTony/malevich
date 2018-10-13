package io.malevich.server.services.registertoken;

import io.malevich.server.entity.RegisterTokenEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface RegisterTokenService {

    List<RegisterTokenEntity> findAll();

    RegisterTokenEntity save(RegisterTokenEntity registerTokenEntity);

    void delete(RegisterTokenEntity registerTokenEntity);

    Optional<RegisterTokenEntity> findByToken(String token);

}
