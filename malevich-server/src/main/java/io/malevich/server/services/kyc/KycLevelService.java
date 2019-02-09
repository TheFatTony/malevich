package io.malevich.server.services.kyc;

import io.malevich.server.domain.KycLevelEntity;
import io.malevich.server.domain.ParticipantEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KycLevelService {

    List<KycLevelEntity> findAll();

    boolean contains(KycLevelEntity requiredLevel, KycLevelEntity testLevel);

    KycLevelEntity getLevel(ParticipantEntity participantEntity);
}
