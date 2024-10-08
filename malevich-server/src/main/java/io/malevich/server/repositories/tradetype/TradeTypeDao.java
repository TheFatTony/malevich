package io.malevich.server.repositories.tradetype;

import io.malevich.server.domain.TradeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TradeTypeDao extends JpaRepository<TradeTypeEntity, String> {

    List<TradeTypeEntity> findAll();

}
