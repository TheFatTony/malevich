package io.malevich.server.services.smsqueue;


import io.malevich.server.domain.SmsQueueEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SmsQueueService {

    List<SmsQueueEntity> findAll();

    SmsQueueEntity find(Long id);

    void create(SmsQueueEntity mailQueue);

    void delete(Long id);

    void sendAllMessages();
}
