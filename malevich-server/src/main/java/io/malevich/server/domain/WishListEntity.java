package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "wish_list")
public class WishListEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @ManyToOne
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @ManyToOne
    private ParticipantEntity participant;

}
