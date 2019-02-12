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
    @Column(name = "amount")
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
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

}
