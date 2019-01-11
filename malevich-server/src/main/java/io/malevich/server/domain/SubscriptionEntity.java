package io.malevich.server.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "subscription")
public class SubscriptionEntity extends AbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "email_id")
    private String emailId;

}
