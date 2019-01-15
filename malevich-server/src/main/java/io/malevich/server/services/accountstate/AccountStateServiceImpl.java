package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.fabric.model.TraderParticipant;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.repositories.accountstate.AccountStateDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountStateServiceImpl implements AccountStateService {

    @Autowired
    private AccountStateDao accountStateDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private TraderParticipantService traderParticipantService;

    @Autowired
    private GalleryParticipantService galleryParticipantService;


    @Override
    @Transactional(readOnly = true)
    public List<AccountStateEntity> findAll() {
        return this.accountStateDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountStateEntity findByArtworkStockAndParty(Long artworkStockId, Long counterpartyId) {
        return accountStateDao.findByArtworkStock_IdAndParty_Id(artworkStockId, counterpartyId);
    }

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

        return accountStateDao.findByParty_Id(counterpartyEntity.getId())
                .stream()
                .filter(s -> s.getArtworkStock() != null && s.getQuantity() > 0)
                .map(s -> s.getArtworkStock())
                .collect(Collectors.toList());
    }


}
