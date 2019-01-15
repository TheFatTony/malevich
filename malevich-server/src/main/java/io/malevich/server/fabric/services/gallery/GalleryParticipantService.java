package io.malevich.server.fabric.services.gallery;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import org.springframework.stereotype.Service;

@Service
public interface GalleryParticipantService extends GenericComposerService<ParticipantEntity> {

    GalleryParticipant getOne();
}
