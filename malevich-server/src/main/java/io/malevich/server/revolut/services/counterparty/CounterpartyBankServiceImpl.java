package io.malevich.server.revolut.services.counterparty;

import io.malevich.server.domain.*;
import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.BankCounterpartyModel;
import io.malevich.server.revolut.model.BankIndividualNameModel;
import io.malevich.server.revolut.model.CounterpartyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CounterpartyBankServiceImpl extends GenericBankServiceImpl implements CounterpartyBankService {


    public CounterpartyBankServiceImpl() {
        super("counterparty");
    }

    @Override
    public CounterpartyModel create(PaymentMethodAccountEntity entity) {
        BankCounterpartyModel bankCounterpartyModel = new BankCounterpartyModel();
        bankCounterpartyModel.setBankCountry(entity.getBankCountry().getId());

        if (entity.getParticipant() instanceof TraderPersonEntity) {
            TraderPersonEntity traderPersonEntity = (TraderPersonEntity) entity.getParticipant();
            bankCounterpartyModel.setIndividualName(
                    new BankIndividualNameModel(
                            traderPersonEntity.getPerson().getFirstName(),
                            traderPersonEntity.getPerson().getLastName()
                    )
            );
        } else {
            OrganizationEntity organizationEntity;

            if (entity.getParticipant() instanceof TraderOrganizationEntity)
                organizationEntity = ((TraderOrganizationEntity) entity.getParticipant()).getOrganization();
            else
                organizationEntity = ((GalleryEntity) entity.getParticipant()).getOrganization();

            bankCounterpartyModel.setCompanyName(organizationEntity.getLegalNameMl().get("en"));
        }

        bankCounterpartyModel.setCurrency("EUR");
        bankCounterpartyModel.setBic(entity.getBic());
        bankCounterpartyModel.setIban(entity.getIban());

        return doPost(bankCounterpartyModel);
    }


}
