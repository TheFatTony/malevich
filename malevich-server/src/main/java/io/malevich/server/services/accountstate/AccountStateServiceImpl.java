package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.fabric.model.ArtworkStockAsset;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.TraderParticipant;
import io.malevich.server.fabric.services.artworkstock.ArtworkStockAssetService;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.artworkstock.ArtworkStockService;
import io.malevich.server.services.counterparty.CounterpartyService;
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
    private CounterpartyService counterpartyService;

    @Autowired
    private TraderParticipantService traderParticipantService;

    @Autowired
    private GalleryParticipantService galleryParticipantService;

    @Autowired
    private ArtworkStockAssetService artworkStockAssetService;

    @Autowired
    private ArtworkStockService artworkStockService;


    @Override
    @Transactional(readOnly = true)
    public AccountStateEntity getWallet() {
        CounterpartyEntity counterpartyEntity = counterpartyService.getCurrent();

        AccountStateEntity accountStateEntity = new AccountStateEntity();

        if ("G".equals(counterpartyEntity.getType().getId())) {
            GalleryParticipant galleryParticipant = galleryParticipantService.getOne();
            accountStateEntity.setParty(counterpartyEntity);
            accountStateEntity.setAmount(galleryParticipant.getBalance());
        } else {
            TraderParticipant traderParticipant = traderParticipantService.getOne();
            accountStateEntity.setParty(counterpartyEntity);
            accountStateEntity.setAmount(traderParticipant.getBalance());
        }


        return accountStateEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkStockEntity> getOwnArtworks() {
        CounterpartyEntity counterpartyEntity = counterpartyService.getCurrent();

        List<ArtworkStockEntity> result = new ArrayList<>();

        for (ArtworkStockAsset asset : artworkStockAssetService.selectOwnedArtworkStocks()) {
            result.add(artworkStockService.find(new Long(asset.getId().replace("resource:io.malevich.network.ArtworkStock#", ""))));
        }

        return result;
    }


}
