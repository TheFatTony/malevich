package io.malevich.server.repositories.counterparty;

import io.malevich.server.domain.CounterpartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CounterpartyDao extends JpaRepository<CounterpartyEntity, Long> {

    List<CounterpartyEntity> findAll();

    CounterpartyEntity findCounterpartyEntitiesByGallery_Id(Long galleryId);

    CounterpartyEntity findCounterpartyEntitiesByTrader_Id(Long traderId);
}
