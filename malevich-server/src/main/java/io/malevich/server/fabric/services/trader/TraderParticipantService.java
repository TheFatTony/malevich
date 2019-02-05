package io.malevich.server.fabric.services.trader;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.TraderParticipant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TraderParticipantService extends GenericComposerService<ParticipantEntity> {

    TraderParticipant getOne();

    List<TraderParticipant> getAll();
}
