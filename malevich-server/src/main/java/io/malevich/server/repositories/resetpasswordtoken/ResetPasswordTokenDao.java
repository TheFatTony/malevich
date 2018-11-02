package io.malevich.server.repositories.resetpasswordtoken;

import io.malevich.server.domain.ResetPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResetPasswordTokenDao extends JpaRepository<ResetPasswordTokenEntity, Long> {

    List<ResetPasswordTokenEntity> findAll();

    Optional<ResetPasswordTokenEntity> findByToken(String token);

}
