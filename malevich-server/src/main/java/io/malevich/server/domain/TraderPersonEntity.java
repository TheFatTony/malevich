package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@javax.persistence.Entity
@Table(name = "trader_person")
@PrimaryKeyJoinColumn(name = "participant_id")
@DiscriminatorValue("TP")
public class TraderPersonEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.ALL)
    private PersonEntity person;

}
