package io.malevich.server.services.mailqueue;


import io.malevich.server.repositories.mailqueue.MailQueueDao;
import io.malevich.server.domain.MailQueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


@Service
public class MailQueueServiceImpl implements MailQueueService {

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private MailQueueDao mailQueueDao;

    protected MailQueueServiceImpl() {
    }

    @Override
    @Transactional
    public List<MailQueueEntity> findAll() {
        return this.mailQueueDao.findAll();
    }

    @Override
    @Transactional
    public MailQueueEntity find(Long id) {
        return this.mailQueueDao.findById(id).get();
    }

    @Override
    @Transactional
    public void create(MailQueueEntity mailQueue) {
        this.mailQueueDao.save(mailQueue);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.mailQueueDao.deleteById(id);
    }

    @Override
    @Transactional
    public void sendAllMail() {
        for (MailQueueEntity item : this.findAll()) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            try {
                helper = new MimeMessageHelper(message, true);
                helper.setTo(item.getRecipient());
                helper.setSubject(item.getHeader());
                helper.setText(item.getBody(), true);
                mailSender.send(message);
                this.delete(item.getId());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
