package io.malevich.server.services.trader;


import com.yinyang.core.server.services.auth.AuthService;
import com.yinyang.core.server.services.user.UserService;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.repositories.trader.TraderDao;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private ParticipantService participantService;


    @Override
    @Transactional(readOnly = true)
    public List<TraderPersonEntity> findAll() {
        return traderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderPersonEntity find(Long id) {
        return traderDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public TraderPersonEntity update(TraderPersonEntity trader) {
//        TraderPersonEntity traderEntity = getCurrentTrader();
//
//        UserEntity user = null;
//        boolean isNew = false;
//        if (traderEntity != null) {
//            trader.setId(traderEntity.getId());
//            trader.getUser().setId(traderEntity.getUser().getId());
//            user = traderEntity.getUser();
//
//            if (traderEntity.getPerson() != null)
//                trader.getPerson().setId(traderEntity.getPerson().getId());
//        } else {
//            user = userService.findByName(authService.getUser().getName());
//            trader.getUser().setId(user.getId());
//            isNew = true;
//        }
//
//        if (isNew) {
//            traderEntity = traderDao.save(trader);
//            CounterpartyEntity counterpartyEntity = new CounterpartyEntity();
//            counterpartyEntity.setTrader(traderEntity);
//            counterpartyEntity.setType(counterpartyTypeService.getTraderType());
//            counterpartyService.save(counterpartyEntity);
//        } else {
//            DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
//            delayedChangeEntity.setTypeId("TRADER");
//            delayedChangeEntity.setPayload(trader);
//            delayedChangeEntity.setReferenceId(trader.getId());
//            delayedChangeEntity.setUser(user);
//            delayedChangeService.save(delayedChangeEntity);
//        }
//
//
//        return traderEntity;
        return null;
    }


    @Override
    @Transactional
    public TraderPersonEntity save(TraderPersonEntity traderEntity) {
        return traderDao.save(traderEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public TraderPersonEntity getCurrentTrader() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return participantEntity instanceof TraderPersonEntity ? (TraderPersonEntity) participantEntity : null;
    }
}
