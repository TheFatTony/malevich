package io.malevich.server.services.subscription;

import io.malevich.server.domain.SubscriptionEntity;
import io.malevich.server.repositories.subscription.SubscriptionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Override
    @Transactional
    public SubscriptionEntity save(SubscriptionEntity entity) {
        return this.subscriptionDao.save(entity);
    }
}
