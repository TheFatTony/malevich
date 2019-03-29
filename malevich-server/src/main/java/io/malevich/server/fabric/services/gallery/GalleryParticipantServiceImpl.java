package io.malevich.server.fabric.services.gallery;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.services.fabricobjects.FabricObjectsService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;


@Slf4j
@Service
public class GalleryParticipantServiceImpl extends GenericComposerServiceImpl<ParticipantEntity> implements GalleryParticipantService {


    @Autowired
    private ParticipantService participantService;

    @Autowired
    private FabricObjectsService fabricObjectsService;

    public GalleryParticipantServiceImpl() {
        super("Gallery");
    }

    @Override
    public void create(ParticipantEntity entity) {
        GalleryParticipant galleryParticipant = new GalleryParticipant();
        galleryParticipant.setId(entity.getId().toString());
        galleryParticipant.setEmail(entity.getUser().getUsername());
        galleryParticipant.setBalance(0D);
        galleryParticipant.setBonuses(0D);

        doPost(galleryParticipant);

        FabricObjectsEntity fabricObjectsEntity = new FabricObjectsEntity();
        fabricObjectsEntity.setReferenceId(entity.getId().toString());
        fabricObjectsEntity.setTypeId("Gallery");
        fabricObjectsEntity.setPayload(entity);

        fabricObjectsService.save(fabricObjectsEntity);
    }

    @Override
    public GalleryParticipant getOne() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
            ResponseEntity<GalleryParticipant> res = restTemplate.exchange(composerUrl + "/Gallery/{gallery}", HttpMethod.GET, null, new ParameterizedTypeReference<GalleryParticipant>() {
            }, participantEntity.getId());
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

    @Override
    public List<GalleryParticipant> getAll() {
        try {
            ResponseEntity<List<GalleryParticipant>> res = restTemplate.exchange(composerUrl + "/Gallery", HttpMethod.GET, null, new ParameterizedTypeReference<List<GalleryParticipant>>() {
            });
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

}
