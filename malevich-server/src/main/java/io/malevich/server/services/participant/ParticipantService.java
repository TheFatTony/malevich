package io.malevich.server.services.participant;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public interface ParticipantService {

    List<ParticipantEntity> findAll();

    Optional<ParticipantEntity> findById(Long participantId);

    ParticipantEntity getCurrent();

    ParticipantEntity convertToEntity(Object payload);

    ParticipantEntity save(ParticipantEntity participantEntity, UserEntity user);

    ParticipantEntity update(ParticipantEntity participantEntity);
}
