package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class TradeHistoryEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    private String effectiveDate;

    @Getter
    @Setter
    private OrderEntity askOrder;

    @Getter
    @Setter
    private OrderEntity bidOrder;

    @Getter
    @Setter
    private Double amount;

    @Getter
    @Setter
    private Long quantity;

}
