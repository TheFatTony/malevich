package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "subscription")
public class SubscriptionEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "email_id")
    private String emailId;

}
