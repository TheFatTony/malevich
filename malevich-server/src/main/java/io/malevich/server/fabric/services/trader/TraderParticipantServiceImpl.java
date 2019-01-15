package io.malevich.server.fabric.services.trader;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.PaymentTransaction;
import io.malevich.server.fabric.model.TraderParticipant;
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
public class TraderParticipantServiceImpl extends GenericComposerServiceImpl<ParticipantEntity> implements TraderParticipantService {

    @Autowired
    private ParticipantService participantService;

    public TraderParticipantServiceImpl() {
        super("Trader");
    }

    @Override
    public void create(ParticipantEntity entity) {
        GalleryParticipant galleryParticipant = new GalleryParticipant();
        galleryParticipant.setBalance(0D);
        galleryParticipant.setEmail(entity.getUser().getUsername());

        doPost(galleryParticipant);
    }

    @Override
    public TraderParticipant getOne() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
            ResponseEntity<TraderParticipant> res = restTemplate.exchange(composerUrl + "/Trader/{trader}", HttpMethod.GET, null, new ParameterizedTypeReference<TraderParticipant>() {}, participantEntity.getUser().getUsername());
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }
}
