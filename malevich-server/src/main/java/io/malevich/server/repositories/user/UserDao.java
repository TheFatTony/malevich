package io.malevich.server.repositories.user;


import io.malevich.server.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String name);
}
