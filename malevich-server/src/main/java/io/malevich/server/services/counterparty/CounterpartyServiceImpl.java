package io.malevich.server.services.counterparty;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.repositories.counterparty.CounterpartyDao;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.services.gallery.GalleryService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.trader.TraderService;
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

    @Autowired
    private ParticipantService participantService;

    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyEntity> findAll() {
        return this.counterpartyDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity findByParticipantId(Long galleryId) {
        return counterpartyDao.findByParticipant_Id(galleryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CounterpartyEntity> findById(Long counterpartyId) {
        return counterpartyDao.findById(counterpartyId);
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity getCurrent() {
        ParticipantEntity participant = participantService.getCurrent();

        if(participant == null)
            return null;

        return findByParticipantId(participant.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity getMalevich() {
        return findById(1L).get();
    }

    @Override
    public CounterpartyEntity save(CounterpartyEntity counterpartyEntity) {
        return counterpartyDao.save(counterpartyEntity);
    }

}
