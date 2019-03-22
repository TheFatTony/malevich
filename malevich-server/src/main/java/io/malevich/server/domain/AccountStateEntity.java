package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;


public class AccountStateEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    private ParticipantEntity participant;

    @Getter
    @Setter
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    private Long quantity;

    @Getter
    @Setter
    private Double amount;

    @Getter
    @Setter
    private Double bonuses;

}