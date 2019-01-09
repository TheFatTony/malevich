package io.malevich.server.fabric.services.gallery;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.TransactionEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.services.GenericComposerService;
import org.springframework.stereotype.Service;

@Service
public interface GalleryParticipantService extends GenericComposerService<GalleryEntity> {

}
