package io.malevich.server.services.trader;


import io.malevich.server.domain.*;
import io.malevich.server.repositories.trader.TraderDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Autowired
    private CounterpartyTypeService counterpartyTypeService;

    @Autowired
    private DelayedChangeService delayedChangeService;


    @Override
    @Transactional(readOnly = true)
    public List<TraderEntity> findAll() {
        return traderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity find(Long id) {
        return traderDao.findById(id).orElse(null);
    }

    @Override
    public TraderEntity findByUserName(String name) {
        return traderDao.findByUserName(name).orElse(null);
    }

    @Override
    @Transactional
    public TraderEntity update(TraderEntity trader) {
        TraderEntity traderEntity = getCurrentTrader();

        UserEntity user = null;
        boolean isNew = false;
        if (traderEntity != null) {
            trader.setId(traderEntity.getId());
            trader.getUser().setId(traderEntity.getUser().getId());
            user = traderEntity.getUser();

            if (traderEntity.getPerson() != null)
                trader.getPerson().setId(traderEntity.getPerson().getId());
        } else {
            user = userService.findByName(getUserName());
            trader.getUser().setId(user.getId());
            isNew = true;
        }

        if (isNew) {
            traderEntity = traderDao.save(trader);
            CounterpartyEntity counterpartyEntity = new CounterpartyEntity();
            counterpartyEntity.setTrader(traderEntity);
            counterpartyEntity.setType(counterpartyTypeService.getTraderType());
            counterpartyService.save(counterpartyEntity);
        } else {
            DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
            delayedChangeEntity.setTypeId("TRADER");
            delayedChangeEntity.setPayload(trader);
            delayedChangeEntity.setReferenceId(trader.getId());
            delayedChangeEntity.setUser(user);
            delayedChangeService.save(delayedChangeEntity);
        }


        return traderEntity;
    }


    @Override
    @Transactional
    public TraderEntity save(TraderEntity traderEntity) {
        return traderDao.save(traderEntity);
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails))
            throw new PreAuthenticatedCredentialsNotFoundException(null);

        UserDetails userDetails = (UserDetails) principal;
        return userDetails.getUsername();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity getCurrentTrader() {
        String username = getUserName();
        TraderEntity traderEntity = findByUserName(username);
        return traderEntity;
    }
}
