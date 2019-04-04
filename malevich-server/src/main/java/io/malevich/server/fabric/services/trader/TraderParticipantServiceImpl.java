package io.malevich.server.fabric.services.trader;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import com.yinyang.core.server.services.auth.AuthService;
import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.TraderParticipant;
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
public class TraderParticipantServiceImpl extends GenericComposerServiceImpl<ParticipantEntity> implements TraderParticipantService {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private FabricObjectsService fabricObjectsService;

    @Autowired
    private AuthService authService;

    public TraderParticipantServiceImpl() {
        super("Trader");
    }

    @Override
    public void create(ParticipantEntity entity) {
        TraderParticipant traderParticipant = new TraderParticipant();
        traderParticipant.setId(entity.getId().toString());
        traderParticipant.setEmail(entity.getUsers().get(0).getUsername());
        traderParticipant.setBalance(0D);
        traderParticipant.setBonuses(0D);

        doPost(traderParticipant);

        FabricObjectsEntity fabricObjectsEntity = new FabricObjectsEntity();
        fabricObjectsEntity.setReferenceId(entity.getId().toString());
        fabricObjectsEntity.setTypeId("ArtworkStock");
        fabricObjectsEntity.setPayload(entity);

        fabricObjectsService.save(fabricObjectsEntity);
    }

    @Override
    public TraderParticipant getOne() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        try {
            ResponseEntity<TraderParticipant> res = restTemplate.exchange(composerUrl + "/Trader/{trader}", HttpMethod.GET, null, new ParameterizedTypeReference<TraderParticipant>() {
            }, participantEntity.getId());
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

    @Override
    public List<TraderParticipant> getAll() {
        try {
            ResponseEntity<List<TraderParticipant>> res = restTemplate.exchange(composerUrl + "/Trader", HttpMethod.GET, null, new ParameterizedTypeReference<List<TraderParticipant>>() {
            });
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }
}
