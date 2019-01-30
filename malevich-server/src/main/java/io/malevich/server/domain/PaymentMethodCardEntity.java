package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@Entity
@Table(name = "payment_method_card")
public class PaymentMethodCardEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "number")
    private String cardNumber;

    @Getter
    @Setter
    @Column(name = "holder")
    private String cardHolder;

    @Getter
    @Setter
    @Column(name = "expiration")
    private String cardExpiration;

    @Getter
    @Setter
    @Column(name = "cvv")
    private String cardCvv;

}
