package io.malevich.server.revolut;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.revolut.model.CounterpartyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Slf4j
public abstract class GenericBankServiceImpl {

    @Value("${malevich.revolut.api.url}")
    protected String bankUrl;

    @Value("${malevich.revolut.api.key}")
    protected String accessToken;

    @Autowired
    protected RestTemplate restTemplate;

    protected String endpoint;

    protected GenericBankServiceImpl(String endpoint) {
        setEndpoint(endpoint);
    }


    protected <TRequest, TResponse> TResponse doPost(TRequest arg, Class<TResponse> responceClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set( "Authorization", "Bearer " + accessToken);

        HttpEntity<Object> requestBody = new HttpEntity(arg, headers);
        try {
            ResponseEntity<TResponse> res = restTemplate.exchange(bankUrl + "/" + endpoint, HttpMethod.POST, requestBody, responceClass);
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


}
