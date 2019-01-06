package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "trader")
@Inheritance(strategy = InheritanceType.JOINED)
public class TraderPersonEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private PersonEntity person;


}
