package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "wish_list")
public class WishListEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    private ParticipantEntity participant;

}
