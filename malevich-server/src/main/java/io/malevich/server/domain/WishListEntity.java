package io.malevich.server.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "wish_list")
public class WishListEntity extends AbstractPersistable<Long> {

    @Getter
    @Setter
    @ManyToOne
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @ManyToOne
    private ParticipantEntity participant;

}
