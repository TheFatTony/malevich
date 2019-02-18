package io.malevich.server.revolut.services.transactions;

import io.malevich.server.revolut.GenericBankServiceImpl;
import io.malevich.server.revolut.model.TransactionModel;
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
import java.util.TimeZone;

@Service
public class TransactionsBankServiceImpl extends GenericBankServiceImpl implements TransactionsBankService {

    public TransactionsBankServiceImpl() {
        super("transactions?from={from}&to={to}");
    }

    private String toIsoFormat(Timestamp timestamp){
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(new Date(timestamp.getTime()));
        return text;
    }

    @Override
    public List<TransactionModel> get(Timestamp from, Timestamp to) {
        return doGet(toIsoFormat(from), toIsoFormat(to));
    }
}
