package io.malevich.server.services.revolutwithdrawalhistory;

import io.malevich.server.domain.RevolutWithdrawalHistoryEntity;
import io.malevich.server.repositories.revolutwithdrawalhistory.RevolutWithdrawalHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RevolutWithdrawalHistoryServiceImpl implements RevolutWithdrawalHistoryService {

    @Autowired
    RevolutWithdrawalHistoryDao revolutWithdrawalHistoryDao;

    @Override
    @Transactional
    public RevolutWithdrawalHistoryEntity save(RevolutWithdrawalHistoryEntity entity){
        return revolutWithdrawalHistoryDao.save(entity);
    }

}
