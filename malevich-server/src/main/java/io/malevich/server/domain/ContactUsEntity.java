package io.malevich.server.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "contact_us")
public class ContactUsEntity extends AbstractPersistable<Long> {



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
