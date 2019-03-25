package io.malevich.server.revolut.services.counterparty;

import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.BankCounterpartyModel;
import io.malevich.server.revolut.model.BankIndividualNameModel;
import io.malevich.server.revolut.model.CounterpartyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;


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
            String[] split = entity.getBeneficiaryName().split(" ", 2);
            bankCounterpartyModel.setIndividualName(
                    new BankIndividualNameModel(
                            split[0],
                            split[1]
                    )
            );
        } else {
            bankCounterpartyModel.setCompanyName(entity.getBeneficiaryName());
        }

        bankCounterpartyModel.setCurrency("EUR");
        bankCounterpartyModel.setBic(entity.getBic());
        bankCounterpartyModel.setIban(entity.getIban());

        return doPost(bankCounterpartyModel);
    }

    private CounterpartyModel doPost(Object arg) {
        HttpEntity<Object> requestBody = getHttpEntity(arg);
        try {
            ResponseEntity<CounterpartyModel> res = restTemplate.exchange(bankUrl + "/" + endpoint, HttpMethod.POST, requestBody, new ParameterizedTypeReference<CounterpartyModel>() {
            });
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }

}
