package io.malevich.server.repositories.counterparty;

import io.malevich.server.domain.CounterpartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterpartyDao extends JpaRepository<CounterpartyEntity, Long> {

    List<CounterpartyEntity> findAll();

    CounterpartyEntity findByParticipant_Id(Long galleryId);
}
