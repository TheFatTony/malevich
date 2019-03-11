package io.malevich.server.blockonomics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@Slf4j
public abstract class GenericWalletServiceImpl {

    @Value("${malevich.blockonomics.api.key}")
    protected String accessToken;

    @Value("${malevich.blockonomics.url}")
    protected String baseUrl;

    @Autowired
    protected RestTemplate restTemplate;

    protected String endpoint;

    protected GenericWalletServiceImpl(String endpoint) {
        setEndpoint(endpoint);
    }


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


}
