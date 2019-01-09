package io.malevich.server.fabric.services.gallery;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.TransactionEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.services.GenericComposerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class GalleryParticipantServiceImpl extends GenericComposerServiceImpl<GalleryEntity> implements GalleryParticipantService {


    public GalleryParticipantServiceImpl() {
        super("Gallery");
    }

    @Override
    public void submit(GalleryEntity entity) {
        GalleryParticipant galleryParticipant = new GalleryParticipant();
        galleryParticipant.setBalance(0D);
        galleryParticipant.setEmail(entity.getUser().getUsername());

        doPost(galleryParticipant);
    }
}
