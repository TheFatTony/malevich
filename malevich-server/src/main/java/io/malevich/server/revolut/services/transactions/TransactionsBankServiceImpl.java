package io.malevich.server.revolut.services.transactions;

import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.TransactionModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionsBankServiceImpl extends GenericBankServiceImpl implements TransactionsBankService {

    public TransactionsBankServiceImpl() {
        super("transactions?count=1000&from={from}&to={to}");
    }

    private String toIsoFormat(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(timestamp);
        return text;
    }

    @Override
    public List<TransactionModel> getTransactions(Timestamp from, Timestamp to) {
        return doGet(toIsoFormat(from), toIsoFormat(to));
    }

    private List<TransactionModel> doGet(Object... arguments) {
        HttpEntity<Object> requestBody = getHttpEntity(null);
        try {
            ResponseEntity<List<TransactionModel>> res =
                    restTemplate.exchange(
                            bankUrl + "/" + endpoint,
                            HttpMethod.GET,
                            requestBody,
                            new ParameterizedTypeReference<List<TransactionModel>>() {},
                            arguments
                    );
            return res.getBody();
        } catch (RestClientException e) {
            String errorResponse = ((HttpStatusCodeException) e).getResponseBodyAsString();
            throw new RuntimeException(errorResponse);
        }
    }
}
