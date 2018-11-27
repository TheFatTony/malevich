package io.malevich.server.services.subscription;

import io.malevich.server.domain.SubscriptionEntity;

public interface SubscriptionService {

    SubscriptionEntity save(SubscriptionEntity entity);
}
