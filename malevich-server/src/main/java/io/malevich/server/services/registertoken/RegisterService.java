package io.malevich.server.services.registertoken;

import com.yinyang.core.server.domain.RegisterTokenEntity;
import com.yinyang.core.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.RegisterFormStepTwoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface RegisterService {

    RegisterTokenEntity saveToken(RegisterTokenEntity registerTokenEntity);

    void deleteToken(RegisterTokenEntity registerTokenEntity);

    Optional<RegisterTokenEntity> findToken(String token);

    RegisterTokenEntity register(RegisterTokenEntity entity, String lang);

    AccessTokenDto register2(String token, RegisterFormStepTwoDto registerInfo);

}
