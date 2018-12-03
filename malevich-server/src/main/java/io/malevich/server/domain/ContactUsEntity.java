package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "contact_us")
public class ContactUsEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
