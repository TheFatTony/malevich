package io.malevich.server.fabric.services.malevich;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.MalevichParticipant;
import org.springframework.stereotype.Service;

@Service
public interface MalevichParticipantService extends GenericComposerService<ParticipantEntity> {

    MalevichParticipant getOne();
}
