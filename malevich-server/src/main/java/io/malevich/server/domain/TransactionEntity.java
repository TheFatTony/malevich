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


@javax.persistence.Entity
@Table(name = "transactions")
public class TransactionEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private TransactionTypeEntity type;

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
    private CounterpartyEntity party;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private CounterpartyEntity counterparty;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    @Column(name = "quantity")
    private Long quantity;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private TransactionGroupEntity group;

}
