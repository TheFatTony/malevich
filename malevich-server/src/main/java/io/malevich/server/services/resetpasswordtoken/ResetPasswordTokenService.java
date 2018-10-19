package io.malevich.server.services.resetpasswordtoken;

import io.malevich.server.entity.ResetPasswordTokenEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface ResetPasswordTokenService {

  List<ResetPasswordTokenEntity> findAll();

  ResetPasswordTokenEntity save(ResetPasswordTokenEntity resetPasswordTokenEntity);

  void delete(ResetPasswordTokenEntity resetPasswordTokenEntity);

  Optional<ResetPasswordTokenEntity> findByToken(String token);
}
