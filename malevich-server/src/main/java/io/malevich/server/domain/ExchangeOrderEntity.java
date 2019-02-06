package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.domain.enums.ExchangeOrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@javax.persistence.Entity
@Table(name = "exchange_order")
public class ExchangeOrderEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodEntity paymentMethod;

    @Getter
    @Setter
    @Column(name = "exchange_name")
    private String exchangeName;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "internal_status")
    private ExchangeOrderStatus internalStatus;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "original_amount")
    private BigDecimal originalAmount;

    @Getter
    @Setter
    @Column(name = "currency_pair")
    private String currencyPair;

    @Getter
    @Setter
    @Column(name = "order_id")
    private String orderId;

    @Getter
    @Setter
    @Column(name = "timestamp")
    private java.sql.Timestamp timestamp;

    @Getter
    @Setter
    @Column(name = "status")
    private String status;

    @Getter
    @Setter
    @Column(name = "cumulative_amount")
    private BigDecimal cumulativeAmount;

    @Getter
    @Setter
    @Column(name = "average_price")
    private BigDecimal averagePrice;

    @Getter
    @Setter
    @Column(name = "fee")
    private BigDecimal fee;

    @Getter
    @Setter
    @Column(name = "leverage")
    private String leverage;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

}
