package io.malevich.server.services.counterparty;

import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.repositories.counterparty.CounterpartyDao;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.services.participant.ParticipantService;
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

    @Autowired
    private GalleryParticipantService galleryParticipantService;

    @Autowired
    private TraderParticipantService traderParticipantService;

    @Autowired
    private CounterpartyTypeService counterpartyTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyEntity> findAll() {
        return this.counterpartyDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity findByParticipantId(Long participantId) {
        return counterpartyDao.findByParticipant_Id(participantId);
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

        if (participant == null)
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
        CounterpartyEntity entity = counterpartyDao.save(counterpartyEntity);

        if (entity.getType().equals(counterpartyTypeService.getGalleryType()))
            galleryParticipantService.submit(entity.getParticipant());
        else if (entity.getType().equals(counterpartyTypeService.getTraderType()))
            traderParticipantService.submit(entity.getParticipant());

        return entity;
    }

}
