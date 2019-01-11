package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "account_state")
public class AccountStateEntity extends YAbstractPersistable<Long> {

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
    private ArtworkStockEntity artworkStock;

    @Getter
    @Setter
    @Column(name = "quantity")
    private Long quantity;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

}
