package io.malevich.server.rest.resources;

import com.stripe.exception.StripeException;
import io.malevich.server.stripe.StripeCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/stripe")
public class StripeResource {

    @Autowired
    private StripeCardService stripeCardService;


    @PostMapping("/pay/{token}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_GALLERY', 'ROLE_TRADER')")
    public ResponseEntity<Void> pay(@PathVariable("token") String token, @PathVariable("amount") Double amount) throws StripeException {
        stripeCardService.createCharge("usd", amount, token);
        return ResponseEntity.ok().build();
    }

}
