package io.malevich.server.dao.registertoken;


import io.malevich.server.entity.RegisterTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterTokenDao extends JpaRepository<RegisterTokenEntity, Long> {


}
