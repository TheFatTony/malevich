package io.malevich.server.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "trader_person")
@PrimaryKeyJoinColumn(name = "participant_id")
public class TraderPersonEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.ALL)
    private PersonEntity person;

}
