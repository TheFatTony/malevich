package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "bitcoin_transfers")
public class BitcoinTransfersEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodEntity paymentMethod;

    @Getter
    @Setter
    @Column(name = "sender_address")
    private String senderAddress;

    @Getter
    @Setter
    @Column(name = "destination_address")
    private String destinationAddress;

    @Getter
    @Setter
    @Column(name = "amount")
    private BigDecimal amount;

    @Getter
    @Setter
    @Column(name = "effective_date")
    private java.sql.Timestamp effectiveDate;

}
