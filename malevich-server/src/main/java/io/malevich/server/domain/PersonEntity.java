package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

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

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private GenderEntity gender;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    @NotNull
    private Timestamp dateOfBirth;


}
