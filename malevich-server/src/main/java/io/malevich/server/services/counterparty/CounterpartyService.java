package io.malevich.server.services.counterparty;

import io.malevich.server.domain.CounterpartyEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface CounterpartyService {

    List<CounterpartyEntity> findAll();

    Optional<CounterpartyEntity> findById(Long counterpartyId);

    CounterpartyEntity getCurrent();

    CounterpartyEntity getMalevich();

    CounterpartyEntity save(CounterpartyEntity counterpartyEntity);

    CounterpartyEntity update(CounterpartyEntity counterpartyEntity);
}
