package io.malevich.server.fabric.services.malevich;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import com.yinyang.core.server.services.auth.AuthService;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.MalevichParticipant;
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


@Slf4j
@Service
public class MalevichParticipantServiceImpl extends GenericComposerServiceImpl<ParticipantEntity> implements MalevichParticipantService {


    @Autowired
    private ParticipantService participantService;

    @Autowired
    private FabricObjectsService fabricObjectsService;

    @Autowired
    private AuthService authService;

    public MalevichParticipantServiceImpl() {
        super("Malevich");
    }

    @Override
    public void create(ParticipantEntity entity) {
        MalevichParticipant galleryParticipant = new MalevichParticipant();
        galleryParticipant.setId(entity.getId().toString());
        galleryParticipant.setEmail(authService.getUserEntity().getUsername());
        galleryParticipant.setBalance(0D);
        galleryParticipant.setBonuses(0D);

        doPost(galleryParticipant);

        FabricObjectsEntity fabricObjectsEntity = new FabricObjectsEntity();
        fabricObjectsEntity.setReferenceId(entity.getId().toString());
        fabricObjectsEntity.setTypeId("Malevich");
        fabricObjectsEntity.setPayload(entity);

        fabricObjectsService.save(fabricObjectsEntity);
    }

    @Override
    public MalevichParticipant getOne() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
            ResponseEntity<MalevichParticipant> res = restTemplate.exchange(composerUrl + "/Malevich/{malevich}", HttpMethod.GET, null, new ParameterizedTypeReference<MalevichParticipant>() {
            }, participantEntity.getId());
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

}
