package io.malevich.server.services.accountstate;

import com.yinyang.core.server.services.auth.AuthService;
import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.TraderParticipant;
import io.malevich.server.fabric.services.artworkstock.ArtworkStockAssetService;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountStateServiceImpl implements AccountStateService {



    @Autowired
    private TraderParticipantService traderParticipantService;

    @Autowired
    private GalleryParticipantService galleryParticipantService;

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
        } else {
            TraderParticipant traderParticipant = traderParticipantService.getOne();
            accountStateEntity.setParticipant(counterpartyEntity);
            accountStateEntity.setAmount(traderParticipant.getBalance());
        }


        return accountStateEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> getOwnArtworks() {
        // TODO refactor this crap above


        ParticipantEntity counterpartyEntity = participantService.getCurrent();

        return artworkStockService.findAllByGalleryId(counterpartyEntity.getId());
    }


}
