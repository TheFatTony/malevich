package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@javax.persistence.Entity
@Table(name = "mail_queue")
public class MailQueueEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "recipient")
    private String recipient;


    @Getter
    @Setter
    @Column(name = "header")
    private String header;


    @Getter
    @Setter
    @Column(name = "body")
    private String body;


    @Getter
    @Setter
    @Column(name = "effective_date")
    private Timestamp effectiveDate;

    public MailQueueEntity() {
    }

    public MailQueueEntity(String recipient, String header, String body) {
        this.recipient = recipient;
        this.header = header;
        this.body = body;
        this.effectiveDate = new Timestamp(System.currentTimeMillis());
    }


}
