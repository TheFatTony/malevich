package io.malevich.server.strip;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public abstract class StripeCardServiceImpl implements StripeCardService {

    @Value("${stripe.api.key}")
    protected String apiKey;


    @Override
    public void createCharge(String currency, Double amount) throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("description", "Example charge");
        params.put("source", "tok_KPte7942xySKBKyrBu11yEpf");
        Charge charge = Charge.create(params);
    }


}
