package io.malevich.server.services.counterpartytype;

import io.malevich.server.domain.CounterpartyTypeEntity;
import io.malevich.server.domain.TransactionTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CounterpartyTypeService {

    List<CounterpartyTypeEntity> findAll();

    CounterpartyTypeEntity getTraderType();

    CounterpartyTypeEntity getGalleryType();

    CounterpartyTypeEntity getMalevichType();
}
