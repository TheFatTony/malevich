package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "trade_history")
public class TradeHistoryEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private OrderEntity askOrder;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private OrderEntity bidOrder;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    @Column(name = "quantity")
    private Long quantity;

}
