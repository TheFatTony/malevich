package io.malevich.server.repositories.subscription;

import io.malevich.server.domain.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDao extends JpaRepository<SubscriptionEntity, Long> {
}
