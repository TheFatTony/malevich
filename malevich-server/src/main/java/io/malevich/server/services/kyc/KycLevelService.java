package io.malevich.server.services.kyc;

import io.malevich.server.domain.KycLevelEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.enums.KycLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KycLevelService {

    List<KycLevelEntity> findAll();

    List<KycLevelEntity> getDetailing(String levelName);

    boolean checkLevel(String testLevel, List<String> requiredLevels);

    void checkLevelOrException(ParticipantEntity participantEntity, KycLevel[] requiredLevels);

    KycLevelEntity getLevel(ParticipantEntity participantEntity);

    void updateLevel(ParticipantEntity participantEntity);
}
