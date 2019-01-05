package io.malevich.server.services.registertoken;

import io.malevich.server.domain.RegisterTokenEntity;
import io.malevich.server.domain.UserEntity;
import io.malevich.server.transfer.RegisterFormStepTwoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface RegisterService {

    RegisterTokenEntity saveToken(RegisterTokenEntity registerTokenEntity);

    void deleteToken(RegisterTokenEntity registerTokenEntity);

    Optional<RegisterTokenEntity> findToken(String token);

    RegisterTokenEntity register(String lang, String userName);

    UserEntity register2(String token, RegisterFormStepTwoDto registerInfo);

}
