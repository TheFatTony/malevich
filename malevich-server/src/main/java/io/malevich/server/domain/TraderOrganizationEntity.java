package io.malevich.server.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@EqualsAndHashCode(callSuper = true)
@javax.persistence.Entity
@Table(name = "trader_organization")
@PrimaryKeyJoinColumn(name = "participant_id")
public class TraderOrganizationEntity extends ParticipantEntity {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.MERGE)
    private OrganizationEntity organization;

}
