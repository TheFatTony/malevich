package io.malevich.server.dao.accountstate;

import io.malevich.server.entity.AccountStateEntity;
import io.malevich.server.entity.CounterpartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountStateDao extends JpaRepository<AccountStateEntity, Long> {

    List<AccountStateEntity> findAll();

    AccountStateEntity findByArtworkStock_IdAndParty_Id(Long artworkStockId, Long counterpartyId);

}
