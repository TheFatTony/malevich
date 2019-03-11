package io.malevich.server.services.exchange;


import io.malevich.server.domain.PaymentMethodEntity;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public interface ExchangeService {


    void placeOrder(Long balance, Wallet wallet, PaymentMethodEntity paymentMethodEntity) throws IOException, InterruptedException, InsufficientMoneyException, ExecutionException;

    void processExecution() throws IOException;
}
