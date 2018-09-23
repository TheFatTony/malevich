package io.malevich.server.dao.mailqueue;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.MailQueueEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MailQueueDao extends Dao<MailQueueEntity, Long> {

    List<MailQueueEntity> findAll();

    MailQueueEntity find(Long id);
}
