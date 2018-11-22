package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "payments")
public class PaymentsEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private CounterpartyEntity party;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private PaymentMethodEntity paymentMethod;

    @Getter
    @Setter
    @Column(name = "amount")
    private Double amount;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private TransactionGroupEntity transactionGroup;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private PaymentTypeEntity paymentType;

}
