package io.malevich.server.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.services.accountstate.AccountStateService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethod.PaymentMethodService;
import io.malevich.server.services.payments.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class StripeCardServiceImpl implements StripeCardService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ParticipantService participantService;

    @Override
    public void createCharge(String currency, Double amount, String token) throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount.intValue() * 100);
        params.put("currency", currency);
        params.put("description", "Example charge");
        params.put("source", token);
        Charge charge = Charge.create(params);


        if ("succeeded".equals(charge.getStatus())) {
            PaymentsEntity paymentsEntity = new PaymentsEntity();
            paymentsEntity.setParticipant(participantService.getCurrent());
            paymentsEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
            paymentsEntity.setAmount(new BigDecimal(amount));
            paymentsService.insert(paymentsEntity);
        } else {
            new RuntimeException("Payment imposable");
        }
    }


}
