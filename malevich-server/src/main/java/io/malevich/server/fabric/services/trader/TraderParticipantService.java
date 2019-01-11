package io.malevich.server.fabric.services.trader;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ParticipantEntity;
import org.springframework.stereotype.Service;

@Service
public interface TraderParticipantService extends GenericComposerService<ParticipantEntity> {

}
