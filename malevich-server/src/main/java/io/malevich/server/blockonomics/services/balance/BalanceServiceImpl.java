package io.malevich.server.blockonomics.services.balance;

import io.malevich.server.blockonomics.GenericWalletServiceImpl;
import io.malevich.server.blockonomics.model.BalanceRequestModel;
import io.malevich.server.blockonomics.model.BalanceResponseModel;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;


@Slf4j
@Service
public class BalanceServiceImpl extends GenericWalletServiceImpl implements BalanceService {

    public BalanceServiceImpl() {
        super("balance");
    }

    @Override
    public BalanceResponseModel get(PaymentMethodBitcoinEntity entity) {
        BalanceRequestModel request = new BalanceRequestModel();
        request.setAddress(entity.getBtcAddress());
        return doPost(request);
    }

    private BalanceResponseModel doPost(Object arg) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Object> requestBody = new HttpEntity(arg, headers);

        try {
            ResponseEntity<BalanceResponseModel> res = restTemplate.exchange(baseUrl + "/" + endpoint, HttpMethod.POST, requestBody, new ParameterizedTypeReference<BalanceResponseModel>() {
            });
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }


}
