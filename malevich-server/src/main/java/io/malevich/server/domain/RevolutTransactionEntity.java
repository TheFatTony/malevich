package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@javax.persistence.Entity
@Table(name = "revolut_transaction")
public class RevolutTransactionEntity {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @JoinColumn(name = "payments_id")
    @OneToOne
    private PaymentsEntity payment;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "state")
    private String state;

    @Getter
    @Setter
    @Column(name = "reference")
    private String reference;

    @Getter
    @Setter
    @Column(name = "account_id")
    private String accountId;

    @Getter
    @Setter
    @Column(name = "amount")
    private BigDecimal amount;

    @Getter
    @Setter
    @Column(name = "currency")
    private String currency;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

}
