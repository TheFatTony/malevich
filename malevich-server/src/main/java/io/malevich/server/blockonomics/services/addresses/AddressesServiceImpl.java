package io.malevich.server.blockonomics.services.addresses;

import io.malevich.server.blockonomics.GenericWalletServiceImpl;
import io.malevich.server.blockonomics.model.BalanceRequestModel;
import io.malevich.server.blockonomics.model.NewAddressModel;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.revolut.model.PaymentRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;


@Slf4j
@Service
public class AddressesServiceImpl extends GenericWalletServiceImpl implements AddressesService {

    public AddressesServiceImpl() {
        super("address");
    }

    @Override
    public void create(PaymentMethodBitcoinEntity entity) {
        NewAddressModel request = new NewAddressModel();
        request.setAddress(entity.getBtcAddress());
        request.setTag(entity.getId().toString());

        doPost(request, endpoint);
    }


    @Override
    public void delete(PaymentMethodBitcoinEntity entity) {
        BalanceRequestModel request = new BalanceRequestModel();
        request.setAddress(entity.getBtcAddress());

        doPost(request, "delete_address");
    }

    private String doPost(Object arg, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set( "Authorization", "Bearer " + accessToken);
        HttpEntity<Object> requestBody = new HttpEntity(arg, headers);

        try {
            ResponseEntity<String> res = restTemplate.exchange(baseUrl + "/" + url, HttpMethod.POST, requestBody, new ParameterizedTypeReference<String>() {
            });
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }



}
