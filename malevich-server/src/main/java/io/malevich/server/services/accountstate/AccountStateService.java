package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountStateService {

    List<AccountStateEntity> findAll();

    AccountStateEntity findByArtworkStockAndParty(Long artworkStockId, Long counterpartyId);

    AccountStateEntity getTraderWallet();

    List<ArtworkStockEntity> getTraderArtworks();
}
