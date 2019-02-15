package io.malevich.server.revolut.services.payments;

import io.malevich.server.revolut.GenericBankServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PaymentsBankServiceImpl extends GenericBankServiceImpl implements PaymentsBankService {


    public PaymentsBankServiceImpl() {
        super("pay");
    }

    @Override
    public void create(Object entity) {

        doPost(entity);
    }


}
