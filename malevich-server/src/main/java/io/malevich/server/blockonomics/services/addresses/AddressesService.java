package io.malevich.server.blockonomics.services.addresses;

import io.malevich.server.blockonomics.GenericWalletService;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.revolut.model.PaymentModel;
import org.springframework.stereotype.Service;

@Service
public interface AddressesService extends GenericWalletService {

    void create(PaymentMethodBitcoinEntity entity);

    void delete(PaymentMethodBitcoinEntity entity);
}
