package io.malevich.server.repositories.usertype;


import io.malevich.server.domain.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeDao extends JpaRepository<UserTypeEntity, Long> {


}
