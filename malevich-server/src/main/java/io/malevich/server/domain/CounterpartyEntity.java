package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "counterparty")
public class CounterpartyEntity extends AbstractPersistable<Long> {



    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private CounterpartyTypeEntity type;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "participant_id")
    private ParticipantEntity participant;

}
