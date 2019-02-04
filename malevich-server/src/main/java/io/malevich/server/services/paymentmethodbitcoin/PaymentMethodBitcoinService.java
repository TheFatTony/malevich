package io.malevich.server.services.paymentmethodbitcoin;

import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentMethodBitcoinService {

    List<PaymentMethodBitcoinEntity> findAll();

    PaymentMethodBitcoinEntity generateBtc();
}
