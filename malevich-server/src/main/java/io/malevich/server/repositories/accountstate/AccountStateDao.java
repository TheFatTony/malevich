package io.malevich.server.repositories.accountstate;

import io.malevich.server.domain.AccountStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountStateDao extends JpaRepository<AccountStateEntity, Long> {

    List<AccountStateEntity> findAll();

    AccountStateEntity findByArtworkStock_IdAndParty_Id(Long artworkStockId, Long counterpartyId);

    List<AccountStateEntity> findByParty_Id(Long counterpartyId);

}
