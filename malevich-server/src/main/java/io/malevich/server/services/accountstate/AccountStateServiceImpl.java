package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.MalevichParticipant;
import io.malevich.server.fabric.model.TraderParticipant;
import io.malevich.server.fabric.services.artworkstock.ArtworkStockAssetService;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.malevich.MalevichParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private ArtworkStockAssetService artworkStockAssetService;

    @Autowired
    private ArtworkStockService artworkStockService;

    @Autowired
    private ParticipantService participantService;


    @Override
    @Transactional(readOnly = true)
    public AccountStateEntity getWallet() {
        ParticipantEntity counterpartyEntity = participantService.getCurrent();

        AccountStateEntity accountStateEntity = new AccountStateEntity();

        if ("G".equals(counterpartyEntity.getType().getId())) {
            GalleryParticipant galleryParticipant = galleryParticipantService.getOne();
            accountStateEntity.setParticipant(counterpartyEntity);
            accountStateEntity.setAmount(galleryParticipant.getBalance());
        } else if ("M".equals(counterpartyEntity.getType().getId())) {
            MalevichParticipant malevichParticipant = malevichParticipantService.getOne();
            accountStateEntity.setParticipant(counterpartyEntity);
            accountStateEntity.setAmount(malevichParticipant.getBalance());
        } else {
            TraderParticipant traderParticipant = traderParticipantService.getOne();
            accountStateEntity.setParticipant(counterpartyEntity);
            accountStateEntity.setAmount(traderParticipant.getBalance());
        }


        return accountStateEntity;
    }


}
