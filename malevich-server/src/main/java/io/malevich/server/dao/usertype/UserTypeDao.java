package io.malevich.server.dao.usertype;


import io.malevich.server.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeDao extends JpaRepository<UserTypeEntity, Long> {


}
