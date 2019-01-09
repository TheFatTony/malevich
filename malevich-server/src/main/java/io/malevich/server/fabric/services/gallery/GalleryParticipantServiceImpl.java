package io.malevich.server.fabric.services.gallery;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.services.GenericComposerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GalleryParticipantServiceImpl extends GenericComposerServiceImpl<ParticipantEntity> implements GalleryParticipantService {


    public GalleryParticipantServiceImpl() {
        super("Gallery");
    }

    @Override
    public void submit(ParticipantEntity entity) {
        GalleryParticipant galleryParticipant = new GalleryParticipant();
        galleryParticipant.setBalance(0D);
        galleryParticipant.setEmail(entity.getUser().getUsername());

        doPost(galleryParticipant);
    }
}
