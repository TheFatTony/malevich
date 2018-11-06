package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "orders")
public class OrderEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private OrderTypeEntity type;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private CounterpartyEntity party;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private TradeTypeEntity tradeType;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private OrderStatusEntity status;

    @Getter
    @Setter
    @Formula(value = "(SELECT o.amount\n" +
            "FROM orders o\n" +
            "WHERE o.type_id = 'BID' AND o.artwork_stock_id = artwork_stock_id\n" +
            "ORDER BY o.amount DESC, o.effective_date ASC\n" +
            "LIMIT 1)")
    private Double bestBid;

    @Getter
    @Setter
    @Formula(value = "(SELECT o.amount\n" +
            "FROM orders o\n" +
            "WHERE o.type_id = 'ASK' AND o.artwork_stock_id = artwork_stock_id\n" +
            "ORDER BY o.amount ASC\n" +
            "LIMIT 1)")
    private Double currentAsk;

}
