package io.malevich.server.services.accesstoken;

import io.malevich.server.entity.AccessTokenEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface AccessTokenService {

  List<AccessTokenEntity> findAll();

    AccessTokenEntity findByToken(String accessToken);

    void delete(AccessTokenEntity accessTokenEntity);

}
