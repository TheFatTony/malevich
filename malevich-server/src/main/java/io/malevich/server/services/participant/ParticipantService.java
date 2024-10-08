package io.malevich.server.services.participant;

import com.yinyang.core.server.domain.UserEntity;
import io.malevich.server.domain.ParticipantEntity;
import org.springframework.stereotype.Service;

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
