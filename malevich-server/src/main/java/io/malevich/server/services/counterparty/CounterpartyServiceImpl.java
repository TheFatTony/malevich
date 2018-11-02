package io.malevich.server.services.counterparty;

import io.malevich.server.repositories.counterparty.CounterpartyDao;
import io.malevich.server.domain.CounterpartyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CounterpartyServiceImpl implements CounterpartyService {

    @Autowired
    private CounterpartyDao counterpartyDao;


    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyEntity> findAll() {
        return this.counterpartyDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity findCounterpartyEntitiesByGalleryId(Long galleryId) {
        return counterpartyDao.findCounterpartyEntitiesByGallery_Id(galleryId);
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity findCounterpartyEntitiesByTraderId(Long traderId) {
        return counterpartyDao.findCounterpartyEntitiesByTrader_Id(traderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CounterpartyEntity> findById(Long counterpartyId) {
        return counterpartyDao.findById(counterpartyId);
    }

}
