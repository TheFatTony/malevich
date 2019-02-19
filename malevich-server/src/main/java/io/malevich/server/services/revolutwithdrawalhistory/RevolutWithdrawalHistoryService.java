package io.malevich.server.services.revolutwithdrawalhistory;

import io.malevich.server.domain.RevolutWithdrawalHistoryEntity;
import org.springframework.stereotype.Service;

@Service
public interface RevolutWithdrawalHistoryService {

    RevolutWithdrawalHistoryEntity save(RevolutWithdrawalHistoryEntity entity);
}
