package io.malevich.server.services.counterparty;

import io.malevich.server.domain.CounterpartyEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public interface CounterpartyService {

    List<CounterpartyEntity> findAll();

    CounterpartyEntity findByParticipantId(Long galleryId);

    Optional<CounterpartyEntity> findById(Long counterpartyId);

    CounterpartyEntity getCurrent();

    CounterpartyEntity getMalevich();

    CounterpartyEntity save(CounterpartyEntity counterpartyEntity);
}
