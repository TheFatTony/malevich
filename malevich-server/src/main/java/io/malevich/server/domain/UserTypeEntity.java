package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "user_type")
public class UserTypeEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(name = "type_name")
    @Getter
    @Setter
    private String typeName;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

}
