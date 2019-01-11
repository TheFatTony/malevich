package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@javax.persistence.Entity
@Table(name = "trade_history")
public class TradeHistoryEntity extends AbstractPersistable<Long> {

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
