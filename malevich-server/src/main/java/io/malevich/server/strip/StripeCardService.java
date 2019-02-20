package io.malevich.server.strip;

import com.stripe.exception.StripeException;

public interface StripeCardService {


    void createCharge(String currency, Double amount) throws StripeException;
}
