package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


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
    @Column(name = "internal_status")
    private String internalStatus;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "original_amount")
    private double originalAmount;

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
    private double cumulativeAmount;

    @Getter
    @Setter
    @Column(name = "average_price")
    private double averagePrice;

    @Getter
    @Setter
    @Column(name = "fee")
    private double fee;

    @Getter
    @Setter
    @Column(name = "leverage")
    private String leverage;

}
