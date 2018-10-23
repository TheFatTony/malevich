package io.malevich.server.dao.resetpasswordtoken;

import io.malevich.server.entity.ResetPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResetPasswordTokenDao extends JpaRepository<ResetPasswordTokenEntity, Long> {

  List<ResetPasswordTokenEntity> findAll();

  Optional<ResetPasswordTokenEntity> findByToken(String token);

}
