package io.malevich.server.fabric.services.trader;

import io.malevich.server.domain.TransactionEntity;
import io.malevich.server.fabric.model.BuySellTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class ComposerTraderServiceImpl implements ComposerTraderService {

    @Value("${malevich.composer.url}")
    private String composerUrl;


    @Value("${malevich.bussines.network.enabled}")
    private boolean enabled;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders fabricHeaders;

    @Override
    public void submitTransction(TransactionEntity transaction) {
        BuySellTransaction buySellTransaction = new BuySellTransaction(
                transaction.getId().toString(),
                transaction.getEffectiveDate().toString(),
                transaction.getParty().getId().toString(),
                transaction.getCounterparty().getId().toString(),
                transaction.getArtworkStock().getId().toString(),
                transaction.getAmount().toString(),
                transaction.getQuantity().toString());
        doPost(buySellTransaction);
    }

    private void doPost(Object arg) {
        if (!enabled)
            return;

        HttpEntity<?> requestBody = new HttpEntity(arg, fabricHeaders);
        try {
            ResponseEntity<String> res = restTemplate.exchange(composerUrl + "/BuySellTransaction", HttpMethod.POST, requestBody, String.class);
        } catch (RestClientException e) {
            String errorResponse=((HttpStatusCodeException)e).getResponseBodyAsString();
            log.trace(errorResponse);
            throw e;
        }
    }
}
