package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@javax.persistence.Entity
@Table(name = "mail_queue")
public class MailQueueEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "recipient")
    @Getter @Setter
    private String recipient;


    @Column(name = "header")
    @Getter @Setter
    private String header;


    @Column(name = "body")
    @Getter @Setter
    private String body;


    @Column(name = "effective_date")
    @Getter @Setter
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
