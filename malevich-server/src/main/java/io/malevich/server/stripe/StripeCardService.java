package io.malevich.server.stripe;

import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public interface StripeCardService {


    void createCharge(String currency, Double amount, String token) throws StripeException;
}
