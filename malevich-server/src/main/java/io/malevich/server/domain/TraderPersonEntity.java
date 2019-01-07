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
@Table(name = "trader_person")
@PrimaryKeyJoinColumn(name = "participant_id")
public class TraderPersonEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private PersonEntity person;


}
