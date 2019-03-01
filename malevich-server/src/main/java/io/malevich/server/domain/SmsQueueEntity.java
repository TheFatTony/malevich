package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = "sms_queue")
public class SmsQueueEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "recipient")
    private String recipient;

    @Getter
    @Setter
    @Column(name = "body")
    private String body;


    @Getter
    @Setter
    @Column(name = "effective_date")
    private Timestamp effectiveDate;

    public SmsQueueEntity() {
    }

    public SmsQueueEntity(String recipient, String body) {
        this.recipient = recipient;
        this.body = body;
        this.effectiveDate = new Timestamp(System.currentTimeMillis());
    }


}
