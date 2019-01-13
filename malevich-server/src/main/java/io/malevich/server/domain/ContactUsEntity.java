package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "contact_us")
public class ContactUsEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Column(name = "email_id")
    @NotNull
    private String emailId;

    @Getter
    @Setter
    @Column(name = "name")
    @NotNull
    private String name;

    @Getter
    @Setter
    @Column(name = "message")
    @NotNull
    private String message;

    @Getter
    @Setter
    @Column(name = "subject")
    @NotNull
    private String subject;

    @Getter
    @Setter
    @Column(name = "phone")
    @NotNull
    private String phone;

}
