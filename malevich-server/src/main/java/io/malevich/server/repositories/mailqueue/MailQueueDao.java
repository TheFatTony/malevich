package io.malevich.server.repositories.mailqueue;

import io.malevich.server.domain.MailQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailQueueDao extends JpaRepository<MailQueueEntity, Long> {


}
