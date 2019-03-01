package io.malevich.server.repositories.smsqueue;

import io.malevich.server.domain.SmsQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsQueueDao extends JpaRepository<SmsQueueEntity, Long> {


}
