package io.malevich.server.scheduling;

import com.yinyang.core.server.services.mailqueue.MailQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MailQueueTask {

    @Autowired
    private MailQueueService mailQueueService;


    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    public void sendAllMail() {
        mailQueueService.sendAllMail();
    }
}
