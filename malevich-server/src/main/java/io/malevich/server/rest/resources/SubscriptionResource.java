package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.SubscriptionEntity;
import io.malevich.server.services.subscription.SubscriptionService;
import io.malevich.server.transfer.SubscriptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/subscribe")
public class SubscriptionResource extends RestResource<SubscriptionDto, SubscriptionEntity> {

    @Autowired
    private SubscriptionService subscriptionService;

    public SubscriptionResource() {
        super(SubscriptionDto.class, SubscriptionEntity.class);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody SubscriptionDto dto) {
        this.subscriptionService.save(convertToEntity(dto));
        return ResponseEntity.ok().build();
    }

}
