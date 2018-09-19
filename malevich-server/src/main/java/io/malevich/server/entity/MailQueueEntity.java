package io.malevich.server.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@javax.persistence.Entity
@Table(name = "mail_queue")
public class MailQueueEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient")
    private String recipient;


    @Column(name = "header")
    private String header;


    @Column(name = "body")
    private String body;


    @Column(name = "effective_date")
    private Timestamp effectiveDate;

    public MailQueueEntity() {
    }

    public MailQueueEntity(String recipient, String header, String body) {
        setId(null);
        setRecipient(recipient);
        setHeader(header);
        setBody(body);
        setEffectiveDate(new Timestamp(System.currentTimeMillis()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Timestamp getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

}
