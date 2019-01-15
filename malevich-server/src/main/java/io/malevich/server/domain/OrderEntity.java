package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@javax.persistence.Entity
@Table(name = "orders")
public class OrderEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private OrderTypeEntity type;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private CounterpartyEntity party;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private TradeTypeEntity tradeType;

    @Getter
    @Setter
    @Column(name = "amount")
    @NotNull
    @Positive
    private Double amount;

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
    private OrderStatusEntity status;

    @Getter
    @Setter
    @Column(name = "expiration_date")
    private java.sql.Timestamp expirationDate;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private TransactionGroupEntity transactionGroup;

    @Getter
    @Setter
    @Transient
    private Double bestBid;

    @Getter
    @Setter
    @Transient
    private Double currentAsk;

    @Getter
    @Setter
    @Transient
    private Boolean isOwn;

}
