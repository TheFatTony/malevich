package io.malevich.server.dao.mailqueue;

import io.malevich.server.entity.MailQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailQueueDao extends JpaRepository<MailQueueEntity, Long> {


}
