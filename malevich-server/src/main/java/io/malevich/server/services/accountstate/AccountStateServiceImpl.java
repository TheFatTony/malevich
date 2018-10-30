package io.malevich.server.services.accountstate;

import io.malevich.server.dao.accountstate.AccountStateDao;
import io.malevich.server.entity.AccountStateEntity;
import io.malevich.server.entity.CounterpartyEntity;
import io.malevich.server.entity.TraderEntity;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.trader.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class AccountStateServiceImpl implements AccountStateService {

    @Autowired
    private AccountStateDao accountStateDao;

    @Autowired
    private TraderService traderService;

    @Autowired
    private CounterpartyService counterpartyService;


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
    public AccountStateEntity getTraderAccountState() {
        TraderEntity traderEntity= traderService.getCurrentTrader();
        CounterpartyEntity counterpartyEntity = counterpartyService.findCounterpartyEntitiesByTraderId(traderEntity.getId());

        return accountStateDao.findByArtworkStock_IdAndParty_Id(null, counterpartyEntity.getId());
    }


}
