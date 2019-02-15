package io.malevich.server.revolut.services.counterparty;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.BankCounterpartyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CounterpartyBankServiceImpl extends GenericBankServiceImpl implements CounterpartyBankService {


    public CounterpartyBankServiceImpl() {
        super("counterparty");
    }

    @Override
    public void create(Object entity) {
        PaymentMethodAccountEntity cEntity = (PaymentMethodAccountEntity) entity;

        BankCounterpartyModel bankCounterpartyModel = new BankCounterpartyModel();
        bankCounterpartyModel.setBankCountry(cEntity.getBankCountry().getNameMl().get("en"));
        bankCounterpartyModel.setCompanyName(cEntity.getParticipant().getUser().getUsername());
        bankCounterpartyModel.setCurrency("EUR");
        bankCounterpartyModel.setBic(cEntity.getBic());
        bankCounterpartyModel.setIban(cEntity.getIban());

        doPost(bankCounterpartyModel);
    }


}
