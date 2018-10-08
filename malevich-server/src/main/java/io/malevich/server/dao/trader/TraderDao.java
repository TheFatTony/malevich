package io.malevich.server.dao.trader;


import io.malevich.server.entity.TraderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<TraderEntity, Long> {

    @Query("select te from TraderEntity te join fetch te.user where te.user.name = :name")
    Optional<TraderEntity> findByUserName(@Param(value = "name") String name);
}
