package io.malevich.server.services.smsqueue;


import io.malevich.server.domain.SmsQueueEntity;
import io.malevich.server.repositories.smsqueue.SmsQueueDao;
import io.malevich.server.twilio.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


@Slf4j
@Service
public class SmsQueueServiceImpl implements SmsQueueService {

    @Autowired
    public SmsService smsService;

    @Autowired
    private SmsQueueDao smsQueueDao;

    protected SmsQueueServiceImpl() {
    }

    @Override
    @Transactional
    public List<SmsQueueEntity> findAll() {
        return this.smsQueueDao.findAll();
    }

    @Override
    @Transactional
    public SmsQueueEntity find(Long id) {
        return this.smsQueueDao.findById(id).get();
    }

    @Override
    @Transactional
    public void create(SmsQueueEntity mailQueue) {
        this.smsQueueDao.save(mailQueue);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.smsQueueDao.deleteById(id);
    }

    @Override
    @Transactional
    public void sendAllMessages() {
        for (SmsQueueEntity item : this.findAll()) {
            smsService.sendSms(item.getRecipient(), item.getBody());
            delete(item.getId());
        }
    }
}
