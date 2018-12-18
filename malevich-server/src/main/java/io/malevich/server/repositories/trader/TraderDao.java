package io.malevich.server.repositories.trader;


import io.malevich.server.domain.TraderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<TraderEntity, Long> {

}
