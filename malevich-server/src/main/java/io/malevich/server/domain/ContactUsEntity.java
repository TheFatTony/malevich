package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


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
    private String emailId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String phone;

}
