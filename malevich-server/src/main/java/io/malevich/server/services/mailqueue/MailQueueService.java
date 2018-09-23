package io.malevich.server.services.mailqueue;


import io.malevich.server.entity.MailQueueEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailQueueService {

    List<MailQueueEntity> findAll();

    MailQueueEntity find(Long id);

    MailQueueEntity create(MailQueueEntity mailQueue);

    void delete(Long id);

    void sendAllMail();
}
