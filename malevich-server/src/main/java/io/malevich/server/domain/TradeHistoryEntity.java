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


@javax.persistence.Entity
@Table(name = "trade_history")
public class TradeHistoryEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Column(name = "effective_date")
    @NotNull
    private java.sql.Timestamp effectiveDate;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private OrderEntity askOrder;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private OrderEntity bidOrder;

    @Getter
    @Setter
    @Column(name = "amount")
    @NotNull
    @Positive
    private Double amount;

    @Getter
    @Setter
    @Column(name = "quantity")
    @NotNull
    @Positive
    private Long quantity;

}
