package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
