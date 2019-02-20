package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@javax.persistence.Entity
@Table(name = "revolut_withdrawal_history")
public class RevolutWithdrawalHistoryEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @JoinColumn(name = "payments_id")
    @OneToOne
    private PaymentsEntity payment;

    @Getter
    @Setter
    @Column(name = "request_id")
    private String requestId;

    @Getter
    @Setter
    @Column(name = "account_id")
    private String accountId;

    @Getter
    @Setter
    @Column(name = "receiver_counterparty_id")
    private String receiverCounterpartyId;

    @Getter
    @Setter
    @Column(name = "receiver_account_id")
    private String receiverAccountId;

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
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

}
