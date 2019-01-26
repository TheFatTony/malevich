package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Transient;


public class OrderEntity {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private OrderTypeEntity type;

    @Getter
    @Setter
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    private ParticipantEntity participant;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    private TradeTypeEntity tradeType;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    private java.sql.Timestamp effectiveDate;

    @Getter
    @Setter
    private OrderStatusEntity status;

    @Getter
    @Setter
    private java.sql.Timestamp expirationDate;

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
