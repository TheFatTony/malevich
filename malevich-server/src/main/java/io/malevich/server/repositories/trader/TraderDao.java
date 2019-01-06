package io.malevich.server.repositories.trader;


import io.malevich.server.domain.TraderOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<TraderOrganizationEntity, Long> {

    @Query("select te from TraderOrganizationEntity te join fetch te.user where te.user.name = :name")
    Optional<TraderOrganizationEntity> findByUserName(@Param(value = "name") String name);
}
