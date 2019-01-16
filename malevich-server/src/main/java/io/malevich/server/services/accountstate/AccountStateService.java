package io.malevich.server.services.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountStateService {

    AccountStateEntity getWallet();

    List<ArtworkStockEntity> getOwnArtworks();
}
