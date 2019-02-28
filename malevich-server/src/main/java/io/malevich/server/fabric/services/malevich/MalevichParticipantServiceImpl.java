package io.malevich.server.fabric.services.malevich;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.MalevichParticipant;
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

    public MalevichParticipantServiceImpl() {
        super("Malevich");
    }

    @Override
    public void create(ParticipantEntity entity) {
        MalevichParticipant galleryParticipant = new MalevichParticipant();
        galleryParticipant.setId(entity.getId().toString());
        galleryParticipant.setEmail(entity.getUser().getUsername());
        galleryParticipant.setBalance(0D);

        doPost(galleryParticipant);
    }

    @Override
    public MalevichParticipant getOne() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
            ResponseEntity<MalevichParticipant> res = restTemplate.exchange(composerUrl + "/Malevich/{malevich}", HttpMethod.GET, null, new ParameterizedTypeReference<MalevichParticipant>() {
            }, participantEntity.getUser().getId());
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

}
