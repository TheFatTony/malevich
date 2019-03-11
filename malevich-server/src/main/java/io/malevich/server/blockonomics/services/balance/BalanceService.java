package io.malevich.server.blockonomics.services.balance;

import io.malevich.server.blockonomics.GenericWalletService;
import io.malevich.server.blockonomics.model.BalanceResponseModel;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import org.springframework.stereotype.Service;

@Service
public interface BalanceService extends GenericWalletService {

    BalanceResponseModel get(PaymentMethodBitcoinEntity entity);
}
