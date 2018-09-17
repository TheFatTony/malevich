package io.malevich.server.dao.mailqueue;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.MailQueueEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaMailQueueDao extends JpaDao<MailQueueEntity, Long> implements MailQueueDao {


    public JpaMailQueueDao() {
        super(MailQueueEntity.class);
    }


}
