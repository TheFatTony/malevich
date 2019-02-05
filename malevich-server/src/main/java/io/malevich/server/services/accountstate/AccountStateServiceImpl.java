package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.CounterpartyParticipant;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.MalevichParticipant;
import io.malevich.server.fabric.model.TraderParticipant;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.malevich.MalevichParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountStateServiceImpl implements AccountStateService {


    @Autowired
    private TraderParticipantService traderParticipantService;

    @Autowired
    private GalleryParticipantService galleryParticipantService;

    @Autowired
    private MalevichParticipantService malevichParticipantService;

    @Autowired
    private ParticipantService participantService;


    @Override
    @Transactional(readOnly = true)
    public AccountStateEntity getWallet() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        AccountStateEntity accountStateEntity;

        if ("G".equals(participantEntity.getType().getId())) {
            GalleryParticipant galleryParticipant = galleryParticipantService.getOne();
            accountStateEntity = getAccountStateFromParticipant(galleryParticipant, participantEntity);
        } else if ("M".equals(participantEntity.getType().getId())) {
            MalevichParticipant malevichParticipant = malevichParticipantService.getOne();
            accountStateEntity = getAccountStateFromParticipant(malevichParticipant, participantEntity);
        } else {
            TraderParticipant traderParticipant = traderParticipantService.getOne();
            accountStateEntity = getAccountStateFromParticipant(traderParticipant, participantEntity);
        }

        return accountStateEntity;
    }

    private AccountStateEntity getAccountStateFromParticipant(CounterpartyParticipant fabricParticicpant, ParticipantEntity participantEntity) {
        AccountStateEntity accountStateEntity = new AccountStateEntity();
        accountStateEntity.setParticipant(participantEntity);
        accountStateEntity.setAmount(fabricParticicpant.getBalance());
        return accountStateEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountStateEntity> getAll() {
        Map<String, ParticipantEntity> entityMap = participantService.findAll()
                .stream()
                .collect(Collectors.toMap(p -> p.getId().toString(), p -> p));

        List<TraderParticipant> fabricTraders = traderParticipantService.getAll();
        List<GalleryParticipant> fabricGalleries = galleryParticipantService.getAll();

        return Stream.concat(fabricTraders.stream(), fabricGalleries.stream())
                .map(p -> getAccountStateFromParticipant(p, entityMap.get(p.getId())))
                .collect(Collectors.toList());
    }


}
